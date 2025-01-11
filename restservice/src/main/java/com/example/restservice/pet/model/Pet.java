package com.example.restservice.pet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;

@Data
@Entity
@Table(name = "pet")
public class Pet {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int age;

    @JsonIgnore
    @ManyToOne
    private Owner owner;

    @JsonIgnore
    @ManyToOne
    private Food food;

}
