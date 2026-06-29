package com.example.backend.controller;

import com.example.backend.security.JwtUtil;
import org.springframework.web.bind.annotation.*;

import org.springframework.security.core.Authentication;


@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public String me(Authentication authentication) {
        if (authentication == null) {
            return "unauthorized";
        }
        return "hello " + authentication.getName();
    }
}

/* 
@RestController
@RequestMapping("/api/user")
public class UserController {

    @GetMapping
    public String me(Authentication authentication) {
        return "hello " + authentication.getName();
    }
}
*/