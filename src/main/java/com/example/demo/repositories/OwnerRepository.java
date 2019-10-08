package com.example.demo.repositories;

import com.example.demo.models.OwnerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OwnerRepository extends JpaRepository<OwnerModel, Integer> {
}
