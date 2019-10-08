package com.example.demo.repositories;

import com.example.demo.models.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PlayerRepository extends JpaRepository<PlayerModel, Integer> {

}
