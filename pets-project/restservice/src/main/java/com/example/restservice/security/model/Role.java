package com.example.restservice.security.model;

import jakarta.persistence.*;

@Entity
@Table(name = "roles")
public class Role {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private String id;
    private String name;

    public String getName() {
        return name;
    }

    public Role(String name) {
        this.name = name;
    }

    public Role() {
    }
}
