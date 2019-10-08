package com.example.demo.repositories;

import com.example.demo.models.MatchPositionModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchPositionRepository extends JpaRepository<MatchPositionModel, Integer> {
}
