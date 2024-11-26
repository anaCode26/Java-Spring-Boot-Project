package com.example.restservice.security.controller;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @PostMapping("/public/user/register")
    public String register() {
        return "OK";
    }

    @PostMapping("/public/user/login")
    public String login() {
        return null;
    }
}
