package com.example.restservice.security.service;

import com.example.restservice.pet.model.Owner;
import com.example.restservice.pet.repository.OwnerRepository;
import com.example.restservice.security.dto.LoginDTO;
import com.example.restservice.security.dto.RegistrationDTO;
import com.example.restservice.security.model.Role;
import com.example.restservice.security.model.User;
import com.example.restservice.security.repository.RoleRepository;
import com.example.restservice.security.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class UserService {

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private JWTService jwtService;

    @Autowired
    private BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder(12);

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private RoleRepository roleRepository;

    public void registerUser(RegistrationDTO registrationDTO) {
        User user = new User();
        user.setEmail(registrationDTO.email());
        user.setPassword(passwordEncoder.encode(registrationDTO.password()));
        Role role = roleRepository.findByName("ROLE_USER");
        Owner owner = new Owner();
        owner.setName(registrationDTO.name());
        owner.setAddress(registrationDTO.address());
        user.setRoles(List.of(role));
        user.setOwner(owner);
        ownerRepository.save(owner);
        userRepository.save(user);
    }

    public String loginUser(LoginDTO loginDto) throws AuthenticationException {
        try {
            Authentication authentication = authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(loginDto.email(), loginDto.password()));
            if (authentication.isAuthenticated()) {
               return jwtService.generateJWT(authentication);
            } else {
                throw new AuthenticationException("Invalid username or password") {
                    @Override
                    public String getMessage() {
                        return super.getMessage();
                    }
                };
            }
        } catch (AuthenticationException e) {
            throw new AuthenticationException("Invalid username or password") {
                @Override
                public String getMessage() {
                    return super.getMessage();
                }
            };
        }
    }

}