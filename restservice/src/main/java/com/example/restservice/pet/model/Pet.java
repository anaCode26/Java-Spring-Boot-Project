package com.example.restservice.pet.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
@AllArgsConstructor
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

    public Pet(){}

    public Pet(Integer id, String name, int age) {
        this.id = id;
        this.name = name;
        this.age = age;
    }

}
