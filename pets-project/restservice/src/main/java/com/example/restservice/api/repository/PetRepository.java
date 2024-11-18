package com.example.restservice.api.repository;

import com.example.restservice.api.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    @Query("SELECT p from Pet p " +
            " LEFT JOIN Food f " +
            " where " +
            " (:nameFilter is NULL OR p.name like :nameFilter) and " +
            " (:olderThan is NULL OR p.age > :olderThan) and " +
            " (:youngerThan is NULL OR p.age < :youngerThan) and " +
            " (:likesVeganFood is NULL OR p.food.isVegan = :likesVeganFood) ")
    List<Pet> getPet(@Param("nameFilter") String name,
                     @Param("olderThan") Integer olderThan,
                     @Param("youngerThan") Integer youngerThan,
                     @Param("likesVeganFood") Boolean likesVeganFood);


    @Query("SELECT p FROM Pet p WHERE p.food.id = :foodId")
    List<Pet> getPetsByFavFoodId(@Param("foodId") Integer foodId);

    @Query("SELECT p FROM Pet p WHERE p.owner.id = :ownerId")
    List<Pet> getPetsByOwnerId(@Param("ownerId") Integer ownerId);

    @Query("SELECT COUNT(p.id) FROM Pet p WHERE p.owner.id = :ownerId")
    Integer getQuantityPetsOwnerId(@Param("ownerId") Integer ownerId);
}
