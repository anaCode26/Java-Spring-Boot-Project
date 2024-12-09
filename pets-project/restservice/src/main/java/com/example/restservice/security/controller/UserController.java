package com.example.restservice.security.controller;

import com.example.restservice.security.dto.LoginDto;
import com.example.restservice.security.model.User;
import com.example.restservice.security.service.JWTService;
import com.example.restservice.security.service.UserService;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;


@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    private JWTService jwtService;

    @PostMapping(value = "/public/user/register", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<String> register(User user) {
        userService.registerUser(user);
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

    @PostMapping ("/public/user/login")
    public ResponseEntity<String> login(@RequestBody LoginDto loginDto, HttpServletResponse response) {
        String token = userService.loginUser(loginDto);
        Cookie cookie = new Cookie("token", token);
        cookie.setHttpOnly(true);
        cookie.setSecure(false);
        cookie.setPath("/api");
        response.addCookie(cookie);
        return ResponseEntity.ok(token);

    }

}
