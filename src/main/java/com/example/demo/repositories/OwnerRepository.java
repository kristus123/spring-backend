package com.example.demo.repositories;

import com.example.demo.models.OwnerModel;
import com.example.demo.models.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OwnerRepository extends JpaRepository<OwnerModel, Integer> {
    Optional<OwnerModel> findByPerson(PersonModel personModel);
}
