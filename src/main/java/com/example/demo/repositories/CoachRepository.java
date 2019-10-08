package com.example.demo.repositories;

import com.example.demo.models.CoachModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CoachRepository extends JpaRepository<CoachModel, Integer> {
}
