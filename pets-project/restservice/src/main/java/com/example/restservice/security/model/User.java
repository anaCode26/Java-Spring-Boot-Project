package com.example.restservice.security.model;

import jakarta.persistence.*;
import org.springframework.lang.NonNull;


@Entity
@Table(name = "users")
public class User {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    int id;

    @Column(nullable = false, unique = true, length = 45)
    String email;
    String role;

    @Column(nullable = false, length = 64)
    String password;

    public User(int id, String email, String role, String password) {
        this.id = id;
        this.email = email;
        this.role = role;
        this.password = password;
    }

    public User() {
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getRole() { return role;}

    public void setRole(String role) { this.role = role;}

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

}