package com.example.backend.config;

import com.example.backend.entity.User;
import com.example.backend.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class DataSeeder implements CommandLineRunner {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void run(String... args) {

        if (userRepository.findByUsername("test").isPresent()) {
            return;
        }

        User user = new User();
        user.setUsername("test");
        user.setPassword(passwordEncoder.encode("12345678"));

        userRepository.save(user);

        System.out.println("初期ユーザー作成完了");
    }
}