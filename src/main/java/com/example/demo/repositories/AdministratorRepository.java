package com.example.demo.repositories;

import com.example.demo.models.AdministratorModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface AdministratorRepository extends JpaRepository<AdministratorModel, Integer> {
}
