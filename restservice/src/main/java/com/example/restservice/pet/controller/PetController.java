package com.example.restservice.pet.controller;

import com.example.restservice.pet.InvalidParameterException;
import com.example.restservice.pet.ResourceNotFoundException;
import com.example.restservice.pet.model.Owner;
import com.example.restservice.pet.model.Pet;
import com.example.restservice.security.model.User;
import com.example.restservice.security.model.UserPrincipal;
import com.example.restservice.service.OwnerService;
import com.example.restservice.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authorization.AuthorizationDecision;
import org.springframework.security.authorization.AuthorizationDeniedException;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/api")
public class PetController {

    @Autowired
    private PetService petService;

    @Autowired
    private OwnerService ownerService;

    @GetMapping("/pet/{id}")
    public Pet getPetById(@PathVariable("id") int id, UserPrincipal userPrincipal) {
        Pet pet = petService.getPetById(id);
        if (pet == null){
            throw new ResourceNotFoundException();
        }
        Owner petOwner = pet.getOwner();

        User user = userPrincipal.getUser();
        Owner userOwner = user.getOwner();

        if(petOwner.getId() != userOwner.getId() && !user.isAdmin()) {
            throw new AuthorizationDeniedException("You don't have access to this resource", new AuthorizationDecision(false));
        }
        return pet;
    }

    @GetMapping("/pet")
    public List<Pet> getPets(@RequestParam("name") Optional<String> name,
                             @RequestParam("olderThan") Optional<Integer> olderThan,
                             @RequestParam("youngerThan") Optional<Integer> youngerThan,
                             @RequestParam("food.isVegan") Optional<Boolean> likesVeganFood,
                             @RequestParam(value = "offset", defaultValue = "0") Integer offset,
                             @RequestParam(value = "limit", defaultValue = "5") Integer limit){
        if(olderThan.isPresent() && olderThan.get() < 0){
            throw new InvalidParameterException("Negative number for olderThan are not allowed.");
        }
        if(youngerThan.isPresent() && youngerThan.get() < 0){
            throw new InvalidParameterException("Negative number for olderThan are not allowed.");
        }
        if(youngerThan.isPresent() && olderThan.isPresent() && (youngerThan.get() < olderThan.get())){
            throw new InvalidParameterException("OlderThan must be less than youngerThan.");
        }

        return petService.getPets(name, olderThan, youngerThan, likesVeganFood, offset, limit);
    }

    @GetMapping("/food/{foodId}/pet")
    public List<Pet> getPetsByFavFoodId(@PathVariable("foodId") int id) {
        return petService.getPetsByFavFood(id);
    }

    @GetMapping("/owner/{ownerId}/pet")
    public List<Pet> getPetsByOwnerId(@PathVariable("ownerId") int id) {
        return petService.getPetsByOwnerId(id);
    }

    @GetMapping("/owner/{ownerId}/quantityPets")
    public Integer getPetAmountByOwnerId(@PathVariable("ownerId") int id) {
        return petService.getPetAmountByOwnerId(id);
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
    public Pet deletePet(@PathVariable("id") int id){ return petService.deletePet(id);
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
        return pet.getName() != null && !pet.getName().isEmpty();
    }
}
