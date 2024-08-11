package com.example.restservice.api.controller;

import com.example.restservice.api.InvalidParameterException;
import com.example.restservice.api.ResourceNotFoundException;
import com.example.restservice.api.model.Pet;
import com.example.restservice.service.PetService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
public class PetController {

    @Autowired
    private PetService petService;

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
                                  @RequestParam("youngerThan") Optional<Integer> youngerThan){
        if(olderThan.isPresent() && olderThan.get() < 0){
            throw new InvalidParameterException("Negative number for olderThan are not allowed.");
        }
        if(youngerThan.isPresent() && youngerThan.get() < 0){
            throw new InvalidParameterException("Negative number for olderThan are not allowed.");
        }
        if(youngerThan.isPresent() && olderThan.isPresent() && (youngerThan.get() < olderThan.get())){
            throw new InvalidParameterException("OlderThan must be less than youngerThan.");
        }

        return petService.getPets(name, olderThan, youngerThan);
    }

    @PostMapping("/pet")
    public Pet createPet(@RequestBody() Pet pet){
        // Validar la mascota
       // Validar que lo que me mandaron este bien: en el controller se suele validar cosas de formato
        return petService.createPet(pet);
    }

    @PutMapping("/pet/{id}")
    public Pet updatePet(@PathVariable("id") int id, @RequestBody() Pet pet){
        return petService.updatePet(id, pet);
    }

    @DeleteMapping("/pet/{id}")
    public Pet deletePet(@PathVariable("id") int id){
        return petService.deletePet(id);
    }

//    @PutMapping("/owner/{ownerId}/pet/{petId}")
//    public Pet updatePetOwner(@PathVariable("petId") int petId,
//                              @PathVariable("ownerId") int newOwnerId) {
//        return petService.updateOwner(petId, newOwnerId);
//    }


}
