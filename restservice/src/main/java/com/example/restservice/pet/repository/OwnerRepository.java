package com.example.restservice.pet.repository;

import com.example.restservice.pet.model.Owner;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface OwnerRepository extends JpaRepository<Owner, Integer> {
    @Query("SELECT o from Owner o " +
            " where " +
            " (:nameFilter is NULL OR o.name like :nameFilter)")
    Page<Owner> getOwner(@Param("nameFilter") String name, Pageable pageable);

    @Query(value = "SELECT o FROM Owner o JOIN FETCH o.pets p")
    List<Owner> fetchOwnerPetsFetch();

    @Query(value = "SELECT o FROM Owner o JOIN o.pets p")
    List<Owner> fetchOwnerPetsNoFetch();

//    @Query(value = " SELECT " +
//            " SUM(f.price) AS total_spent " +
//            " FROM owners o " +
//            "JOIN owners o ON p.pet_id = o.pet_id " +
//            "JOIN pets p ON f.food_id = p.food_id" )
//    Owner getAmountCostPerOwner();


}
