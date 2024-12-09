package com.example.restservice.security;

import com.example.restservice.security.service.CustomUserDetailsService;
import com.example.restservice.security.service.JWTService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.context.ApplicationContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.Arrays;

@Component
public class JwtFilter extends OncePerRequestFilter {

    @Autowired
    private JWTService jwtService;

    @Autowired
    ApplicationContext context;


    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //un filter es un codigo que se ejecuta antes de llegar al controller
        //este filter se ejecuta en todos los request
        if (request.getCookies() == null) {
            filterChain.doFilter(request, response);
            return;
        }

        String authorizationHeader = Arrays.stream(request.getCookies()).
                            filter(cookie -> cookie.getName().equals("token")).findFirst()
                            .map(Cookie::getValue)
                            .orElse(null);

        String jwtToken = null;
        String email = null;

        if (authorizationHeader != null) {
            jwtToken = authorizationHeader;
            email = jwtService.extractUserName(jwtToken);
        }

        if(email != null &&  SecurityContextHolder.getContext().getAuthentication() == null) {
            UserDetails userDetails = context.getBean(CustomUserDetailsService.class).loadUserByUsername(email);

            if(jwtService.validateToken(jwtToken, userDetails)) {
                UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(
                        userDetails, null, userDetails.getAuthorities());
                authentication.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authentication);
            }
        }
        filterChain.doFilter(request, response);
    }
}
