package com.example.restservice.security.controller;

import com.example.restservice.security.SecurityConfig;
import com.example.restservice.security.dto.LoginDto;
import com.example.restservice.security.model.User;
import com.example.restservice.security.repository.UserRepository;
import com.example.restservice.security.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.*;

@RestController
public class UserController {
    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @PostMapping(value = "/public/user/register", consumes = "application/x-www-form-urlencoded")
    public ResponseEntity<String> register(@RequestParam("email") String email,
                                           @RequestParam("password") String password) {
        userService.registerUser(email, password);
        return new ResponseEntity<>("User registered success!", HttpStatus.OK);
    }

//    @PostMapping("/public/user/login")
//    public ResponseEntity<String> login(@RequestBody LoginDto loginDto) {
//        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
//                loginDto.email(),loginDto.password()));
//        SecurityContextHolder.getContext().setAuthentication(authentication);
//        return new ResponseEntity<>("User signed success!", HttpStatus.OK);
//    }

    @PostMapping("/public/user/login")
    public String login(@RequestBody User user) {
       return userService.loginUser(user);
    }

}
