package com.example.restservice.pet.controller;

import com.example.restservice.pet.InvalidParameterException;
import com.example.restservice.pet.ResourceNotFoundException;
import com.example.restservice.pet.model.Owner;
import com.example.restservice.pet.model.Pet;
import com.example.restservice.pet.repository.OwnerRepository;
import com.example.restservice.pet.repository.PetRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.TestPropertySource;

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

	@Autowired
	OwnerRepository ownerRepository;

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
	}

	@Test
	void updatePet_invalidName_throwsException() {
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
	void updatePet_invalidAge_throwsException() {
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

	@Test
	void deletePet_nonExistingPet_throws() {
		//Arrange
		int NON_EXISTING_PET_ID = 0;

		// Assert
		assertThrows(ResourceNotFoundException.class,
				() -> petController.deletePet(NON_EXISTING_PET_ID));
	}

	@Test
	void updatePetOwner_validPetOwner_updatesPetOwner(){
		//Arrange
		Pet initialPet = new Pet();
		initialPet.setName("Popi");
		initialPet.setAge(4);
		Pet savedPet = petRepository.save(initialPet);

		Owner initialOwner = new Owner();
		initialOwner.setName("Rodri");
		Owner savedOwner = ownerRepository.save(initialOwner);

		// Act
		petController.updatePetOwner(savedPet.getId(), savedOwner.getId());

		// Assert
		Pet updatedPet = petRepository.findById(savedPet.getId()).orElseThrow();
		assertEquals(savedOwner.getId(), updatedPet.getOwner().getId());
	}

	@Test
	void updatePetOwner_ownerWith2Pets_throws(){
		//Arrange
		Owner initialOwner = new Owner();
		initialOwner.setName("Rodri");
		Owner savedOwner = ownerRepository.save(initialOwner);

		Pet pet1 = new Pet();
		pet1.setName("Popi");
		pet1.setAge(4);
		pet1.setOwner(savedOwner);
		petRepository.save(pet1);

		Pet pet2 = new Pet();
		pet2.setName("Popi");
		pet2.setAge(4);
		pet2.setOwner(savedOwner);
		petRepository.save(pet2);

		Pet pet3 = new Pet();
		pet2.setName("Popa");
		pet2.setAge(3);
		Pet savedPet3 = petRepository.save(pet3);

		// Act + assert
		assertThrows(InvalidParameterException.class,
				() -> petController.updatePetOwner(savedPet3.getId(), savedOwner.getId()));
	}

	@Test
	void updatePetOwner_petOlderThan10Years_throws(){
		//Arrange
		Owner initialOwner = new Owner();
		initialOwner.setName("Rodri");
		Owner savedOwner = ownerRepository.save(initialOwner);

		Pet pet = new Pet();
		pet.setName("Popi");
		pet.setAge(12);
		Pet petOld = petRepository.save(pet);

		// Act + assert
		assertThrows(InvalidParameterException.class,
				() -> petController.updatePetOwner(petOld.getId(), savedOwner.getId()));
	}

	@Test
	void updatePetOwner_petAlreadyHasOwner_throws() {
		//Arrange
		Owner initialOwner = new Owner();
		initialOwner.setName("Rodri");
		Owner savedOwner = ownerRepository.save(initialOwner);

		Pet pet = new Pet();
		pet.setName("Popi");
		pet.setAge(4);
		pet.setOwner(savedOwner);
		petRepository.save(pet);

		Owner secondOwner = new Owner();
		secondOwner.setName("Pepe");
		Owner savedSecondOwner = ownerRepository.save(secondOwner);

		// Act + assert
		assertThrows(InvalidParameterException.class,
				() -> petController.updatePetOwner(pet.getId(), savedSecondOwner.getId()));

	}

	@Test
	void updatePetOwner_petNotExist_throws() {
		//Arrange
		Owner initialOwner = new Owner();
		initialOwner.setName("Rodri");
		Owner savedOwner = ownerRepository.save(initialOwner);

		int NON_EXISTING_PET_ID = 0;

		// Act + assert
		assertThrows(ResourceNotFoundException.class,
				() -> petController.updatePetOwner(NON_EXISTING_PET_ID, savedOwner.getId()));
	}

	@Test
	void updatePetOwner_ownerNotExist_throws() {
		//Arrange
		int NON_EXISTING_OWNER_ID = 0;

		Pet pet = new Pet();
		pet.setName("Popi");
		pet.setAge(4);
		petRepository.save(pet);

		// Act + assert
		assertThrows(ResourceNotFoundException.class,
				() -> petController.updatePetOwner(pet.getId(), NON_EXISTING_OWNER_ID));
	}

	@Test
	void getPetAmountByOwner_oneOwnerZeroPet_returnZeroPet() {
		//Arrange
		Owner owner = new Owner();
		owner.setName("Anita");
		Owner savedOwner = ownerRepository.save(owner);

		// Act
		int actualPetAmount = petController.getPetAmountByOwnerId(savedOwner.getId());

		// Assert
		assertEquals(0, actualPetAmount);
	}

	@Test
	void getPetAmountByOwner_oneOwnerOnePet_returnOnePet() {
		//Arrange
		Owner owner = new Owner();
		owner.setName("Anita");
		Owner savedOwner = ownerRepository.save(owner);

		Pet pet = new Pet();
		pet.setName("Manchis");
		pet.setAge(4);
		pet.setOwner(savedOwner);
		petRepository.save(pet);

		// Act
		int actualPetAmount = petController.getPetAmountByOwnerId(savedOwner.getId());

		// Assert
		assertEquals(1, actualPetAmount);
	}

	@Test
	void getPetAmountByOwner_oneOwnerTwoPets_returnTwoPet() {
		//Arrange
		Owner owner = new Owner();
		owner.setName("Anita");
		Owner savedOwner = ownerRepository.save(owner);

		Pet pet = new Pet();
		pet.setName("Manchis");
		pet.setAge(4);
		pet.setOwner(savedOwner);
		petRepository.save(pet);

		Pet pet2 = new Pet();
		pet2.setName("Rayis");
		pet2.setAge(4);
		pet2.setOwner(savedOwner);
		petRepository.save(pet2);

		// Act
		int actualPetAmount = petController.getPetAmountByOwnerId(savedOwner.getId());


		// Assert
		assertEquals(2, actualPetAmount);
	}
}
