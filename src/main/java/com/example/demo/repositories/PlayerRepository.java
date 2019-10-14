package com.example.demo.repositories;

import com.example.demo.models.PersonModel;
import com.example.demo.models.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayerModel, Integer> {
    public Optional<PlayerModel> findByPerson(PersonModel person);
}
