package com.example.restservice.service;

import com.example.restservice.api.ResourceNotFoundException;
import com.example.restservice.api.model.Owner;
import com.example.restservice.api.repository.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public OwnerService() {
    }

    public Owner getOwnerById(int id) {
        Optional<Owner> owner = ownerRepository.findById(id);
        return owner.orElse(null);
    }

    public Owner createOwner(Owner owner) {
        ownerRepository.save(owner);
        return owner;
    }

    public List<Owner> getOwners(String name) {
        return ownerRepository.getOwner(name);
    }


    public Owner updateOwner(int id, Owner dataToUpdateOwner) {
        Owner existingOwner = getOwnerById(id);
        if (existingOwner == null) {
            throw new ResourceNotFoundException();
        }
        existingOwner.setName(dataToUpdateOwner.getName());
        existingOwner.setAddress(dataToUpdateOwner.getAddress());
        ownerRepository.save(existingOwner);
        return existingOwner;
    }

    public Owner deleteOwner(int id) {
        Owner ownerToDelete = getOwnerById(id);
        if (ownerToDelete == null) {
            throw new ResourceNotFoundException();
        }
        ownerRepository.delete(ownerToDelete);
        return ownerToDelete;
    }

    public List<Owner> getOwnersWithPets(){
        List<Owner> owners = ownerRepository.fetchOwnerPetsNoFetch();
        owners.get(0).getPets().forEach(pet -> {});
        owners.get(1).getPets();
        return owners;
    }

}
