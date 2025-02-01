package com.example.restservice.pet.controller;

import com.example.restservice.pet.ResourceNotFoundException;
import com.example.restservice.pet.dto.FoodPreference;
import com.example.restservice.pet.model.Food;
import com.example.restservice.pet.model.Owner;
import com.example.restservice.pet.model.Pet;
import com.example.restservice.security.model.User;
import com.example.restservice.security.model.UserPrincipal;
import com.example.restservice.service.FoodService;
import com.example.restservice.service.OwnerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/api")
public class FoodController {

    @Autowired
    private FoodService foodService;

    @Autowired
    private OwnerService ownerService;

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
    public Food updateFood(@PathVariable("id") int id, @RequestBody() Food food, @AuthenticationPrincipal UserPrincipal userPrincipal) {
        Food petsFood = getFoodById(id);
        if (petsFood == null) {
            throw new ResourceNotFoundException();
        }

        Food foodPet = petsFood.getPets().stream().map(Pet::getFood).toList().get(0);
        User user = userPrincipal.getUser();
        Owner userOwner = user.getOwner();

        if(foodPet.getId() != userOwner.getId() && !user.isAdmin() ) {
            throw new AuthorizationDeniedException("You don't have access to this resource", new AuthorizationDecision(false));

        }

        return foodService.updateFood(id, food);
    }

    @DeleteMapping("food/{id}")
    public Food deleteFood(@PathVariable("id") int id) { return foodService.deleteFood(id);}

    @GetMapping("food-preference")
    public List<FoodPreference> getFoodWithPetPreferences() {
        return foodService.getFoodsWithPetPreferences();
    }

}
