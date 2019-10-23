package com.example.demo.repositories;

import com.example.demo.models.PersonModel;
import com.example.demo.models.PlayerModel;
import com.example.demo.models.TeamModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PlayerRepository extends JpaRepository<PlayerModel, Integer> {
    Optional<PlayerModel> findByPerson(PersonModel person);

    List<PlayerModel> findByTeam(TeamModel teamModel);

}
