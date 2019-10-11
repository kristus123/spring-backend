package com.example.demo.repositories;

import com.example.demo.models.AssociationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AssociationRepository extends JpaRepository<AssociationModel, Integer> {
    Optional<AssociationModel> findByName(String name);
}
