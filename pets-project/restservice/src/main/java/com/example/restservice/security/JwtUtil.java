package com.example.restservice.security;

import org.springframework.security.core.userdetails.UserDetails;

public class JwtUtil {
    private static final String SECRET_KEY = "yourSecretKey";

    public String generateToken(UserDetails userDetails) {
        return null;
    }

    public String getUsernameFromToken(String token) {
        return null;
    }

    public boolean validateToken(String token, UserDetails userDetails) {
        return true;
    }
}
