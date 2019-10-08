package com.example.demo.repositories;

import com.example.demo.models.GoalTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GoalTypeRepository extends JpaRepository<GoalTypeModel, Integer> {
}
