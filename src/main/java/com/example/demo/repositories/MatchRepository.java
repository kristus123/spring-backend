package com.example.demo.repositories;

import com.example.demo.models.MatchModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface MatchRepository extends JpaRepository<MatchModel, Integer> {
}
