package com.example.restservice.api.repository;

import com.example.restservice.api.model.Food;
import com.example.restservice.api.model.Pet;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    @Query("SELECT f from Food f where " +
            " (:nameFilter is NULL OR f.name like :nameFilter)")
    List<Food> getFood(@Param("nameFilter") String name);
}
