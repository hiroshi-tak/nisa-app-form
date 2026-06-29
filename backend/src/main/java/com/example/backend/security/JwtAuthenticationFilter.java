package com.example.backend.security;

import java.util.List;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import jakarta.servlet.http.Cookie;
import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.io.IOException;


public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Override
    protected void doFilterInternal(
            HttpServletRequest request,
            HttpServletResponse response,
            FilterChain filterChain
    ) throws ServletException, IOException {

        System.out.println("=== JWT FILTER START ===");
        System.out.println("URI: " + request.getRequestURI());

        String token = null;
/* 
        String uri = request.getRequestURI();

        if (uri.startsWith("/api/auth/logout")) {
            filterChain.doFilter(request, response);
            return;
        }
*/
        try {
            // Authorization header
            String header = request.getHeader("Authorization");
            System.out.println("AUTH HEADER: " + header);

            if (header != null && header.startsWith("Bearer ")) {
                token = header.substring(7);
            }

            // Cookie fallback
            if (token == null && request.getCookies() != null) {
                
                for (Cookie c : request.getCookies()) {
                    System.out.println("COOKIE: " + c.getName() + "=" + c.getValue());
                    if ("token".equals(c.getName())) {
                        token = c.getValue();
                    }
                }
            }

            if (token == null) {
                System.out.println("NO TOKEN → SKIP AUTH");
                filterChain.doFilter(request, response);
                return;
            }

            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(JwtUtil.getKey())
                    .build()
                    .parseClaimsJws(token)
                    .getBody();

            String username = claims.getSubject();

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(username, null, List.of())
            );

            System.out.println("AUTH SUCCESS: " + username);

        } catch (Exception e) {
            System.out.println("JWT ERROR: " + e.getClass().getSimpleName());
            System.out.println("MSG: " + e.getMessage());

            SecurityContextHolder.clearContext();
        }

        filterChain.doFilter(request, response);
    }
}