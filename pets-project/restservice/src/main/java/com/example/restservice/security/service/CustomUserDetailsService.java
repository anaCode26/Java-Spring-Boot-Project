package com.example.restservice.security.service;

import com.example.restservice.security.repository.UserRepository;
import com.example.restservice.security.model.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;


@Service
public class CustomUserDetailsService implements UserDetailsService {

    private UserRepository userRepository;
    public CustomUserDetailsService(UserRepository userRepository){
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        User user = userRepository.findByEmail(email)
                .orElseThrow(() -> new UsernameNotFoundException("User not found with email" + email));
        return org.springframework.security.core.userdetails.User.withUsername(user.getEmail())
                .password(user.getPassword()).roles(user.getRole()).build();
    }
}
