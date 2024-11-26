package com.example.restservice.pet.repository;

import com.example.restservice.security.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    @Query(" SELECT u FROM User u ")
    User findByEmail(String email);
}
