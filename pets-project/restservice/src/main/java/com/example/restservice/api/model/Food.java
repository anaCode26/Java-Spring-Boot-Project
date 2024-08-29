package com.example.restservice.api.model;

import jakarta.persistence.*;

import java.util.Set;

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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getBrand() {
        return brand;
    }

    public void setBrand(String brand) {
        this.brand = brand;
    }

    public Boolean getVegan() {
        return isVegan;
    }

    public void setVegan(Boolean vegan) {
        isVegan = vegan;
    }

    public Set<Pet> getPets() {
        return pets;
    }

    public void setPets(Set<Pet> pets) {
        this.pets = pets;
    }
}
