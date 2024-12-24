package com.example.restservice.security.dto;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class RegistrationDTO {

    private String email;
    private String password;
    private String name;
    private String address;
}
