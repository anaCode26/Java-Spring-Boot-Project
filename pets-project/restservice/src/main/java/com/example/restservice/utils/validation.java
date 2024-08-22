package com.example.restservice.utils;

import com.example.restservice.api.InvalidParameterException;
import com.example.restservice.api.model.Pet;

public class validation {
    public static boolean validatePet(Pet pet) {
        if (pet.getName() == null || pet.getName().isEmpty()) {
            throw new InvalidParameterException("Name must not be null or empty");
        }

        if (pet.getAge() <= 0) {
            throw new InvalidParameterException("Age should be a positive number");
        }

        return true;
    }
}
