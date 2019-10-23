package com.example.demo.repositories;

import com.example.demo.models.CoachModel;
import com.example.demo.models.PersonModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CoachRepository extends JpaRepository<CoachModel, Integer> {
    Optional<CoachModel> findByPerson(PersonModel personModel);

}
