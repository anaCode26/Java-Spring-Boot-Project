package com.example.restservice.service;

import com.example.restservice.api.model.Food;
import com.example.restservice.api.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class FoodService {

    @Autowired
    private FoodRepository foodRepository;

    public Food getFoodById(int id) {
        Optional<Food> food = foodRepository.findById(id);
        return food.orElse(null);
    }

    public List<Food> getFoods(String name) {
        return foodRepository.getFood(name);
    }

    public Food createFood(Food food) {
        foodRepository.save(food);
        return food;
    }
}
