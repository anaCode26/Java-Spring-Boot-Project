package com.example.restservice.security.dto;

public record RegistrationDTO(
        String email,
        String password,
        String name,
        String address) {}
