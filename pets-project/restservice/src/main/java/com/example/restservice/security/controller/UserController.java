package com.example.restservice.security.controller;

import com.example.restservice.security.model.User;
import com.example.restservice.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/public/user/register", consumes = "application/x-www-form-urlencoded")
    public String register(@RequestParam("email") String email, @RequestParam("password") String password) {
        userService.registerUser(email, password);
        return "OK Register";
    }

    @PostMapping("/public/user/login")
    public String login() {
        return "Ok Login";
    }
}
