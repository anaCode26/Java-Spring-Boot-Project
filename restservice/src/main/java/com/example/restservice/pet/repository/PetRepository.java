package com.example.restservice.pet.repository;

import com.example.restservice.pet.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Page;
import java.util.List;
import java.util.Optional;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    Optional<Pet> findById(int id);

    @Query("SELECT p from Pet p " +
            " LEFT JOIN Food f ON p.food = f " +
            " where " +
            " (:nameFilter is NULL OR p.name like :nameFilter) and " +
            " (:olderThan is NULL OR p.age > :olderThan) and " +
            " (:youngerThan is NULL OR p.age < :youngerThan) and " +
            " (:likesVeganFood is NULL OR f.isVegan = :likesVeganFood)")
    Page<Pet> getPet(
                     @Param("nameFilter") String name,
                     @Param("olderThan") Integer olderThan,
                     @Param("youngerThan") Integer youngerThan,
                     @Param("likesVeganFood") Boolean likesVeganFood,
                     Pageable pageable);

    @Query("SELECT p FROM Pet p WHERE p.food.id = :foodId")
    List<Pet> getPetsByFavFoodId(@Param("foodId") Integer foodId);

    @Query("SELECT p FROM Pet p WHERE p.owner.id = :ownerId")
    List<Pet> getPetsByOwnerId(@Param("ownerId") Integer ownerId);

    @Query("SELECT COUNT(p.id) FROM Pet p WHERE p.owner.id = :ownerId")
    Integer getPetAmountByOwnerId(@Param("ownerId") Integer ownerId);
}
