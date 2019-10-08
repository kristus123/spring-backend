package com.example.demo.repositories;

import com.example.demo.models.TeamModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TeamRepository extends JpaRepository<TeamModel, Integer> {

}
