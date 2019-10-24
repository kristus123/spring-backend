package com.example.demo.repositories;

import com.example.demo.models.GoalTypeModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface GoalTypeRepository extends JpaRepository<GoalTypeModel, Integer> {

    public Optional<GoalTypeModel> findByTypeName(String name);
    public GoalTypeModel deleteByTypeName(String name);
}
