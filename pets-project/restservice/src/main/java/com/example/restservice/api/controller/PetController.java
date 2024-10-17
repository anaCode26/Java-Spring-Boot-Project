package com.example.restservice.api.controller;

import com.example.restservice.api.InvalidParameterException;
import com.example.restservice.api.ResourceNotFoundException;
import com.example.restservice.api.model.Owner;
import com.example.restservice.api.model.Pet;
import com.example.restservice.service.OwnerService;
import com.example.restservice.service.PetService;
import com.example.restservice.utils.validation;
import org.hibernate.engine.jdbc.Size;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Objects;
import java.util.Optional;


@RestController
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/pet/{id}")
    public Pet getPetById(@PathVariable("id") int id) {
        Pet pet = petService.getPetById(id);
        if (pet == null){
            throw new ResourceNotFoundException();
        }
        return pet;
    }

    @GetMapping("/pet")
    public List<Pet> getPets(@RequestParam("name") Optional<String> name,
                                  @RequestParam("olderThan") Optional<Integer> olderThan,
                             @RequestParam("youngerThan") Optional<Integer> youngerThan,
                             @RequestParam("food.isVegan") Optional<Boolean> likesVeganFood){
        if(olderThan.isPresent() && olderThan.get() < 0){
            throw new InvalidParameterException("Negative number for olderThan are not allowed.");
        }
        if(youngerThan.isPresent() && youngerThan.get() < 0){
            throw new InvalidParameterException("Negative number for olderThan are not allowed.");
        }
        if(youngerThan.isPresent() && olderThan.isPresent() && (youngerThan.get() < olderThan.get())){
            throw new InvalidParameterException("OlderThan must be less than youngerThan.");
        }

        return petService.getPets(name, olderThan, youngerThan, likesVeganFood);
    }

    @GetMapping("/food/{foodId}/pet")
    public List<Pet> getPetsByFavFoodId(@PathVariable("foodId") int id) {
        return petService.getPetsByFavFood(id);
    }

    @PostMapping("/pet")
    public Pet createPet(@RequestBody() Pet pet){
        return petService.createPet(pet);
    }

    @PutMapping("/pet/{id}")
    public Pet updatePet(@PathVariable("id") int id, @RequestBody() Pet pet){
        if(!isNameValid(pet)) {
            throw new InvalidParameterException("Name must not be null or empty");
        }

        if(!isAgePositiveNumber(pet)) {
            throw new InvalidParameterException("Age must be positive");
        }
        return petService.updatePet(id, pet);
    }

    @PatchMapping("/pet/{id}")
    public Pet updatePetPartially(@PathVariable("id") int id, @RequestBody() Pet pet){
        return petService.updatePetPartially(id, pet);
    }

    @DeleteMapping("/pet/{id}")
    public Pet deletePet(@PathVariable("id") int id){
        return petService.deletePet(id);
    }

    @PutMapping("/owner/{ownerId}/pet/{petId}")
    public Pet updatePetOwner(@PathVariable("petId") int petId,
                              @PathVariable("ownerId") int newOwnerId) {
        Owner newOwner = ownerService.getOwnerById(newOwnerId);
        return petService.updateOwner(petId, newOwner);
    }


    public static boolean isAgePositiveNumber(Pet pet) {
        return pet.getAge() > 0;
    }

    public static boolean isNameValid(Pet pet) {
        return !pet.getName().isEmpty() || pet.getName() != null;
    }
}
