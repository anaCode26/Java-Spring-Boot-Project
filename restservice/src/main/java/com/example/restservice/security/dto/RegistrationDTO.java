package com.example.restservice.security.dto;

public class RegistrationDTO {

    private String email;
    private String password;
    private String name;
    private String address;

    public RegistrationDTO() {}
    public RegistrationDTO(String email, String password, String name, String address) {}

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }
}
