package com.example.restservice.security.dto;

public record LoginDto (
        String email,
        String password,
        String role
){
}
