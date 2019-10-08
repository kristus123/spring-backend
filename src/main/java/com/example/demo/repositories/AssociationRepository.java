package com.example.demo.repositories;

import com.example.demo.models.AssociationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AssociationRepository extends JpaRepository<AssociationModel, Integer> {
}
