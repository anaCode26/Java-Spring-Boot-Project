package com.example.restservice.api.controller;

import com.example.restservice.api.model.Pet;
import com.example.restservice.api.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
class PetControllerTest {

	@Autowired
	PetController petController;

	@Mock
	PetRepository petRepository;

	@BeforeEach
	public void setup() {
		Pet createdPet = new Pet();
		createdPet.setId(1);
		when(petRepository.save(any())).thenReturn(createdPet);
	}

	@Test
	void contextLoads() {
		// Arrange
		Pet pet = new Pet();
		pet.setName("Manchis");
		pet.setAge(4);

		//Act
		Pet createdPet = petController.createPet(pet);

		// Assert
		assertEquals(1, createdPet.getId());
	}

}
