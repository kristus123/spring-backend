package com.example.demo.repositories;

import com.example.demo.models.MatchGoalModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchGoalRepository extends JpaRepository<MatchGoalModel, Integer> {
}
