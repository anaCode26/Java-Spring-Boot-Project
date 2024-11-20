package com.example.restservice.service;

import com.example.restservice.api.InvalidParameterException;
import com.example.restservice.api.ResourceNotFoundException;
import com.example.restservice.api.model.Owner;
import com.example.restservice.api.model.Pet;
import com.example.restservice.api.repository.FoodRepository;
import com.example.restservice.api.repository.OwnerRepository;
import com.example.restservice.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.PageRequest;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    @Autowired
    private OwnerRepository ownerRepository;

    @Autowired
    private FoodRepository foodRepository;

    public PetService() {}

    public Pet getPetById(int id) {
        Optional<Pet> pet = petRepository.findById(id);
        return pet.orElse(null);
    }
    public Owner getOwnerByPet(int id) {
        Optional<Owner> owner = ownerRepository.findById(id);
        return owner.orElse(null);
    }

    public List<Pet> getPets(Optional<String> name, Optional<Integer> olderThan, Optional<Integer> youngerThan,
                             Optional<Boolean> likesVeganFood, Integer offset, Integer limit) {
        // TODO: no devolver.toList()
        return petRepository.getPet(
                name.orElse(null),
                olderThan.orElse(null),
                youngerThan.orElse(null),
                likesVeganFood.orElse(null),
                PageRequest.of((offset / limit), limit))
                .toList();
    }

    public List<Pet> getPetsByFavFood(int id) {
        return petRepository.getPetsByFavFoodId(id);
    }

    public  List<Pet> getPetsByOwnerId(int id) {
        return petRepository.getPetsByOwnerId(id);
    }

    public  Integer getPetAmountByOwnerId(int id) {
        return petRepository.getPetAmountByOwnerId(id);
    }

    public Pet createPet(Pet pet){
        petRepository.save(pet);
        return pet;
    }

    public Pet updatePet(int id, Pet dataToUpdatePet) {
        Pet existingPet = getPetById(id);
        if (existingPet == null){
            throw new ResourceNotFoundException();
        }
        existingPet.setName(dataToUpdatePet.getName());
        existingPet.setAge(dataToUpdatePet.getAge());
        petRepository.save(existingPet);
        return existingPet;
    }

    public Pet updatePetPartially(int id, Pet dataToUpdatePet) {
        Pet existingPet = getPetById(id);
        if (existingPet == null){
            throw new ResourceNotFoundException();
        }
        existingPet.setName(dataToUpdatePet.getName() != null ? dataToUpdatePet.getName() : existingPet.getName());
        existingPet.setAge(dataToUpdatePet.getAge() != 0 ? dataToUpdatePet.getAge() : existingPet.getAge() );
        petRepository.save(existingPet);
        return existingPet;
    }

    public Pet deletePet(int id) {
        Pet petToDelete = getPetById(id);
        if (petToDelete == null) {
            throw new ResourceNotFoundException();
        }
        petRepository.delete(petToDelete);
        return petToDelete;
    }

    public Pet updateOwner(int petId, Owner newOwner) {
        Pet existingPet = getPetById(petId);
        int MAX_PETS_ALLOW = 2;
        if (existingPet == null ){
            throw new ResourceNotFoundException();
        }
        if (existingPet.getOwner() != null) {
            throw new InvalidParameterException("The pet already has an owner.");
        }
        if (existingPet.getAge() > 10) {
            throw new InvalidParameterException("You can't adopt a pet older than 10 years.");
        }

        if (newOwner == null) {
            throw new ResourceNotFoundException();
        }

        if (newOwner.getPets().size() >= MAX_PETS_ALLOW) {
            throw new InvalidParameterException("The owner has enough pets.");
        }
        existingPet.setOwner(newOwner);
        petRepository.save(existingPet);
        return existingPet;
    }



}
