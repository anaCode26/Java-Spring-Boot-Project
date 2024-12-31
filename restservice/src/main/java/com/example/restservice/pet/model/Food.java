package com.example.restservice.pet.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
public class Food {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;
    private String name;
    private double price;
    private String brand;
    private Boolean isVegan;

    @OneToMany(mappedBy = "food", fetch = FetchType.LAZY)
    private Set<Pet> pets;

    public Food() {}

    public Food(int id, String name, double price, String brand, Boolean isVegan) {
        this.id = id;
        this.name = name;
        this.price = price;
        this.brand = brand;
        this.isVegan = isVegan;
    }

}
