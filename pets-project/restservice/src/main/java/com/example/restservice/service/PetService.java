package com.example.restservice.service;

import com.example.restservice.api.ResourceNotFoundException;
import com.example.restservice.api.model.Owner;
import com.example.restservice.api.model.Pet;
import com.example.restservice.api.repository.PetRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class PetService {

    @Autowired
    private PetRepository petRepository;

    public PetService() {}

    public Pet getPetById(int id) {
        Optional<Pet> pet = petRepository.findById(id);
        return pet.orElse(null);
    }

    public List<Pet> getPets(Optional<String> name, Optional<Integer> olderThan, Optional<Integer> youngerThan) {
        return petRepository.getPet(name.orElse(null), olderThan.orElse(null), youngerThan.orElse(null));
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
        // TODO: corregir, debe aceptar null o cero como valor
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

    // TODO: hacer funcionar el metodo que actualiza el owner de un pet
    public Pet updateOwner(int petId, Owner newOwner) {
        Pet existingPet = getPetById(petId);
        if (existingPet == null){
            throw new ResourceNotFoundException();
        }
        existingPet.setOwner(newOwner);
        petRepository.save(existingPet);
        return existingPet;
    }
}
