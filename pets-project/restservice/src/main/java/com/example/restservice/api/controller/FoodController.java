package com.example.restservice.api.controller;

import com.example.restservice.api.model.Food;
import com.example.restservice.api.model.Owner;
import com.example.restservice.api.model.Pet;
import com.example.restservice.service.FoodService;
import com.example.restservice.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Set;

@RestController
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/food/{id}")
    public Food getFoodById(@PathVariable("id") int id) { return foodService.getFoodById(id); }

    @GetMapping("/food/{foodId}/pet")
    public List<Pet> getPetsByFavFoodId(@PathVariable("foodId") int id) {
        return foodService.getPetsByFavFood(id);
    }

    @GetMapping("/food")
    public List<Food> getFood(@RequestParam("name") String name) {
        return foodService.getFoods(name);
    }

    @PostMapping("/food")
    public Food createFood(@RequestBody() Food food) {
        return foodService.createFood(food);
    }

    @PutMapping("/food/{id}")
    public Food updateFood(@PathVariable("id") int id, @RequestBody() Food food) {
        return foodService.updateFood(id, food);
    }

    @DeleteMapping("food/{id}")
    public Food deleteFood(@PathVariable("id") int id) { return foodService.deleteFood(id);}
}
