package com.example.demo.repositories;

import com.example.demo.models.MatchModel;
import com.example.demo.models.TeamModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchRepository extends JpaRepository<MatchModel, Integer> {

}
