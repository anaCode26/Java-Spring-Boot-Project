package com.example.restservice.pet.model;

import jakarta.persistence.*;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Setter
@Getter
@Data
@Entity
@Table(name="owner")
public class Owner {

    @Id
    @Column(name = "id")
    @SequenceGenerator(name = "owner_sequence", sequenceName = "owner_sequence", allocationSize = 1)
    @GeneratedValue(strategy = GenerationType.IDENTITY, generator = "owner_sequence")
    private int id;
    private String name;
    private String address;

    @OneToMany(mappedBy = "owner", fetch = FetchType.EAGER)
    private Set<Pet> pets;

    public Owner() {}

    public Owner(int id, String name, String address) {
        this.id = id;
        this.name = name;
        this.address = address;
    }

}
