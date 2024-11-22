package com.example.restservice.api.controller;

import com.example.restservice.api.model.User;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Bean
    public BCryptPasswordEncoder bCryptPasswordEncoder() {
        return new BCryptPasswordEncoder();
    }
    @PostMapping("/register")
    public String registerUser() {
        return null;
    }

    @PostMapping("/login")
    public String loginUser() {
        return null;
    }
}
