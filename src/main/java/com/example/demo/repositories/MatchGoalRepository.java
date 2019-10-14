package com.example.demo.repositories;

import com.example.demo.models.MatchGoalModel;
import com.example.demo.models.MatchModel;
import com.example.demo.models.PlayerModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface MatchGoalRepository extends JpaRepository<MatchGoalModel, Integer> {
    List<MatchGoalModel> findByPlayer(PlayerModel player);

    List<MatchGoalModel> findByMatch(MatchModel match);

}
