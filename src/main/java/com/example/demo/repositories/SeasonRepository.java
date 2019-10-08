package com.example.demo.repositories;

import com.example.demo.models.SeasonModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SeasonRepository extends JpaRepository<SeasonModel, Integer> {
}
