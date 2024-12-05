package com.example.restservice.security;

import com.example.restservice.security.service.CustomUserDetailsService;
import com.example.restservice.security.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.catalina.core.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

public class JwtFilter extends OncePerRequestFilter {

    //@Autowired
    private JWTService jwtService;

    //@Autowired
    ApplicationContext context;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorizationHeader = request.getHeader("Authorization");
        String jwtToken = null;
        String email = null;

        if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
            jwtToken = authorizationHeader.substring(7);
            email = jwtService.extractUserName(jwtToken);
        }

//        if(email != null &&  SecurityContextHolder.getContext().getAuthentication() == null) {
//            UserDetails userDetails = context();
//
//            if(jwtService.generateJWT(jwtToken, userDetails)) {
//
//            }
//        }
    }
}
