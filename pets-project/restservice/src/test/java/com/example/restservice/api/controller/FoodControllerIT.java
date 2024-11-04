package com.example.restservice.api.controller;

import com.example.restservice.api.dto.FoodPreference;
import com.example.restservice.api.model.Food;
import com.example.restservice.api.model.Pet;
import com.example.restservice.api.repository.FoodRepository;
import com.example.restservice.api.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.springframework.test.util.AssertionErrors.assertTrue;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
public class FoodControllerIT {

    @Autowired
    FoodController foodController;

    @Autowired
    FoodRepository foodRepository;

    @Autowired
    PetRepository petRepository;

    @BeforeEach
    public void setup() {
        petRepository.deleteAll();
        foodRepository.deleteAll();
    }

    @Test
    public void getFoodWithPetPreferences_noFoodNoPets_returnsEmpty(){
        // Arrange

        //Act
        List<FoodPreference> preferences = foodController.getFoodWithPetPreferences();
        // Assert
        assertEquals(0, preferences.size());
    }

    @Test
    public void getFoodWithPetPreferences_twoFoodsNoPets_returnsFoodsWith0Like(){
        // Arrange
        Food food1 = new Food();
        food1.setName("Canin kitty");
        food1.setPrice(250);
        food1.setBrand("Royal");

        Food food2 = new Food();
        food2.setName("Canin Adult");
        food2.setPrice(300);
        food2.setBrand("Royal");
        Food savedFood1 = foodRepository.save(food1);
        Food savedFood2 = foodRepository.save(food2);

        foodRepository.save(savedFood1);
        foodRepository.save(savedFood2);


        //Act
        List<FoodPreference> preferences = foodController.getFoodWithPetPreferences();
        // Assert
        assertEquals(2, preferences.size());
        assertEquals("Canin kitty", preferences.get(0).foodName());
        assertEquals(0, preferences.get(0).quantity());
        assertEquals("Canin Adult", preferences.get(1).foodName());
        assertEquals(0, preferences.get(1).quantity());

    }

    @Test
    public void getFoodWithPetPreferences_oneFoodAndOnePet_returnsFoodWith1Like(){
        // Arrange
        Pet pet = new Pet();
        pet.setName("Panchito");
        pet.setAge(8);
        Food food = new Food();
        food.setName("Royal sabor Manzana");
        food.setPrice(20);
        food.setBrand("Royal");

        Food savedFood = foodRepository.save(food);
        Pet savedPet = petRepository.save(pet);

        savedFood.setPets(Set.of(savedPet));
        savedPet.setFood(savedFood);

        petRepository.save(savedPet);
        foodRepository.save(savedFood);

        //Act
        List<FoodPreference> preferences = foodController.getFoodWithPetPreferences();
        // Assert
        assertEquals(1, preferences.size());
        assertEquals(1, preferences.get(0).quantity());
        assertEquals("Royal sabor Manzana", preferences.get(0).foodName());
    }

    @Test
    public void getFoodWithPetPreferences_twoFoodsAndOnePet_returnsFoodsLikes(){
        // Arrange
        Pet pet = new Pet();
        pet.setName("rayis");
        pet.setAge(3);

        Food food1 = new Food();
        food1.setName("Royal kitty salmon");
        food1.setBrand("Royal");
        food1.setPrice(280);

        Food food2 = new Food();
        food2.setName("Royal adult lamb");
        food2.setBrand("Royal");
        food2.setPrice(340);

        Pet savedPet = petRepository.save(pet);
        Food savedFood1 = foodRepository.save(food1);
        Food savedFood2 = foodRepository.save(food2);

        savedFood1.setPets(Set.of(savedPet));
        savedPet.setFood(savedFood1);

        foodRepository.save(savedFood1);
        foodRepository.save(savedFood2);

        //Act

        List<FoodPreference> preferences = foodController.getFoodWithPetPreferences();
        // Assert
        assertEquals(2, preferences.size());
        assertEquals("Royal kitty salmon", preferences.get(0).foodName());
        assertEquals(1, preferences.get(0).quantity());
        assertEquals("Royal adult lamb", preferences.get(1).foodName());
        assertEquals(0, preferences.get(1).quantity());

    }

}
