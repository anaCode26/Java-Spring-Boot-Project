package com.example.restservice.api.repository;

import com.example.restservice.api.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface PetRepository extends JpaRepository<Pet, Integer> {
    @Query("SELECT p from Pet p where " +
            " (:nameFilter is NULL OR p.name like :nameFilter) and " +
            " (:olderThan is NULL OR p.age > :olderThan) and " +
            " (:youngerThan is NULL OR p.age < :youngerThan)")
    List<Pet> getPet(@Param("nameFilter") String name,
                           @Param("olderThan") Integer olderThan,
                           @Param("youngerThan") Integer youngerThan);
}
