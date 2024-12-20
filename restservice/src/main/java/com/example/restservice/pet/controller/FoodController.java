package com.example.restservice.pet.controller;

import com.example.restservice.pet.dto.FoodPreference;
import com.example.restservice.pet.model.Food;
import com.example.restservice.service.FoodService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @GetMapping("/food/{id}")
    public Food getFoodById(@PathVariable("id") int id) { return foodService.getFoodById(id); }

    @GetMapping("/food")
    public List<Food> getFood(@RequestParam("name") String name,
                              @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                              @RequestParam(value = "limit", defaultValue = "5") Integer limit) {
        return foodService.getFood(name, offset, limit);
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

    @GetMapping("food-preference")
    public List<FoodPreference> getFoodWithPetPreferences() {
        return foodService.getFoodsWithPetPreferences();
    }

}
