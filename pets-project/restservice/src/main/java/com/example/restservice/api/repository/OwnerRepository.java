package com.example.restservice.api.repository;

import com.example.restservice.api.model.Owner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    @Query("SELECT o from Owner o where " +
            " (:nameFilter is NULL OR o.name like :nameFilter)")
    List<Owner> getOwner(@Param("nameFilter") String name);

    @Query(value = "SELECT o FROM Owner o JOIN FETCH o.pets p")
    List<Owner> fetchOwnerPetsFetch();

    @Query(value = "SELECT o FROM Owner o JOIN o.pets p")
    List<Owner> fetchOwnerPetsNoFetch();

}
