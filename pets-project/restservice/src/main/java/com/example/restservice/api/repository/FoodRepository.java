package com.example.restservice.api.repository;

import com.example.restservice.api.model.Food;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface FoodRepository extends JpaRepository<Food, Integer> {
    @Query(value = "SELECT f from Food f " +
            " where " +
            " (:nameFilter is NULL OR f.name like :nameFilter)")
    Page<Food> getFood(@Param("nameFilter") String name, Pageable pageable);

    @Query(value = "SELECT f FROM Food f LEFT JOIN FETCH f.pets p")
    List<Food> fetchFoodWithPets();

}
