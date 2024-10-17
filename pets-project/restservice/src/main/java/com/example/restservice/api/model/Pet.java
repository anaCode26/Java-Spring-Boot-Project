package com.example.restservice.api.model;


import com.fasterxml.jackson.annotation.JsonIgnore;
import io.micrometer.common.lang.NonNull;
import jakarta.persistence.*;
import org.antlr.v4.runtime.misc.NotNull;

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

    public Integer getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public int getAge() {
        return age;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Owner getOwner() {
        return owner;
    }

    public Food getFood() { return food;}

    public void setOwner(Owner owner) {
        this.owner = owner;
    }

    public void setFood(Food food) {
        this.food = food;
    }
}
