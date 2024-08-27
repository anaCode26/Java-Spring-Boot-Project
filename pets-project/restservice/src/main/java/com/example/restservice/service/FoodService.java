package com.example.restservice.service;

import com.example.restservice.api.InvalidParameterException;
import com.example.restservice.api.ResourceNotFoundException;
import com.example.restservice.api.model.Food;
import com.example.restservice.api.model.Pet;
import com.example.restservice.api.repository.FoodRepository;
import com.example.restservice.api.repository.PetRepository;
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

    public Food updateFood(int id, Food dataToUpdateFood) {
        Food existingFood = getFoodById(id);
        if (existingFood == null) {
            throw new ResourceNotFoundException();
        }
        existingFood.setName(dataToUpdateFood.getName());
        existingFood.setBrand(dataToUpdateFood.getBrand());
        existingFood.setPrice(dataToUpdateFood.getPrice());
        foodRepository.save(existingFood);
        return existingFood;
    }

    public Food deleteFood(int id) {
        Food foodToDelete = getFoodById(id);
        if (foodToDelete == null) {
            throw new ResourceNotFoundException();
        }
        foodRepository.delete(foodToDelete);
        return foodToDelete;
    }
}
