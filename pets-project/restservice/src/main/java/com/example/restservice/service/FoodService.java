package com.example.restservice.service;

import com.example.restservice.api.ResourceNotFoundException;
import com.example.restservice.api.dto.FoodPreference;
import com.example.restservice.api.model.Food;
import com.example.restservice.api.repository.FoodRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.Iterator;
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

    public List<Food> getFoods(String name, Integer offset, Integer limit) {
        return foodRepository.getFood(name, PageRequest.of((offset / limit), limit)).toList();
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

    public List<FoodPreference> getFoodsWithPetPreferences() {
        List<Food> foods = foodRepository.fetchFoodWithPets();

        return foods.stream()
                .map(food -> new FoodPreference(food.getName(), food.getPets().size()))
                .toList();
    }
}
