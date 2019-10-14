package com.example.demo.repositories;

import com.example.demo.models.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonModel, Integer> {
    Optional<PersonModel> findByFirstName(String firstName);
}
