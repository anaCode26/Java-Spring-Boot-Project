package com.example.restservice.api.controller;

import com.example.restservice.api.InvalidParameterException;
import com.example.restservice.api.ResourceNotFoundException;
import com.example.restservice.api.model.Pet;
import com.example.restservice.api.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;

@SpringBootTest
@ExtendWith(MockitoExtension.class)
@TestPropertySource("classpath:application-test.properties")
@ActiveProfiles("test")
class PetControllerIT {

	@Autowired
	PetController petController;

	@Autowired
	PetRepository petRepository;

	@BeforeEach
	public void setup() {
		petRepository.deleteAll();
	}

	@Test
	public void createPet_validPet_returnsNewPet(){
		// Arrange
		Pet pet = new Pet();
		pet.setName("Manchis");
		pet.setAge(4);

		//Act
		Pet createdPet = petController.createPet(pet);

		// Assert
		assertNotNull(createdPet.getId());
	}

	@Test
	void updatePet_updateName_returnsPetNameUpdated() {
		// Arrange
		Pet initialPet = new Pet();
		initialPet.setName("Popi");
		initialPet.setAge(4);
		Pet savedPet = petController.createPet(initialPet);
		savedPet.setName("Mengano");

		// Act
		Pet updatedPet = petController.updatePet(savedPet.getId(), savedPet);

		Optional<Pet> petFromBD = petRepository.findById(updatedPet.getId());

		// Assert
		assertTrue(petFromBD.isPresent());
		assertEquals("Mengano",petFromBD.get().getName());
		// TODO: agregar aca que obtenga el pet de la db con el repository y valide que el atributo haya cambiado
	}

	@Test
	void updatePet_validPet_returnsPetAgeUpdated() {
		// Arrange
		Pet initialPet = new Pet();
		initialPet.setName("Popi");
		initialPet.setAge(4);
		Pet savedPet = petController.createPet(initialPet);
		savedPet.setAge(8);

		// Act
		Pet updatedPet = petController.updatePet(savedPet.getId(), savedPet);

		Optional<Pet> petFromBD = petRepository.findById(updatedPet.getId());

		// Assert
		assertTrue(petFromBD.isPresent());
		assertEquals(petFromBD.get().getAge(),updatedPet.getAge());
		// TODO: agregar aca que obtenga el pet de la db con el repository y valide que el atributo haya cambiado
	}

	@Test
	void updatePet_invalidName_returnsException() {
			// Arrange
			Pet initialPet = new Pet();
			initialPet.setName("Popi");
			initialPet.setAge(4);
			Pet savedPet = petController.createPet(initialPet);
			savedPet.setName(null);

			// Act
			// Assert
			assertThrows(InvalidParameterException.class,
					() -> petController.updatePet(savedPet.getId(), savedPet));
	}

	@Test
	void updatePet_invalidAge_returnsException() {
		// Arrange
		Pet initialPet = new Pet();
		initialPet.setName("Popi");
		initialPet.setAge(4);
		Pet savedPet = petController.createPet(initialPet);
		savedPet.setAge(0);

		// Act
		// Assert
		assertThrows(InvalidParameterException.class,
				() -> petController.updatePet(savedPet.getId(), savedPet));
	}

	// testear get 1 caso positiov y 1 negativo

	// agregar test delete 1 caso negativo
	@Test
	void deletePet_existingPet_getsDeletedFromDb() {
		// Arrange
		Pet initialPet = new Pet();
		initialPet.setName("Popi");
		initialPet.setAge(4);
		Pet savedPet = petController.createPet(initialPet);

		// Act
		petController.deletePet(savedPet.getId());

		// Assert
		Optional nonExistingPet = petRepository.findById(savedPet.getId());
		assertTrue(nonExistingPet.isEmpty());
	}


}
