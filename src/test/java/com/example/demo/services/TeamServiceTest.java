package com.example.demo.services;

import com.example.demo.models.TeamModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class TeamServiceTest {

    @Autowired
    TeamService teamService;

    /*

    @Test
    void save() {

        String associationName = "ManU";
        TeamModel team = new TeamModel(1, 1, associationName);
        TeamModel savedTeam = teamService.save(team);

        assertEquals(associationName, savedTeam.getAssociation().getAssociation_name());
    }

    @Test
    void delete() {

        Integer team_id = 3;
        TeamModel team = new TeamModel(team_id, 3, "Chelsea");
        teamService.save(team);
        teamService.delete(team);

        Optional<TeamModel> fetchedTeam = teamService.findById(team_id);
        assertFalse(fetchedTeam.isPresent());

    }

    @Test
    void findById() {

        Integer team_id = 2;
        TeamModel team = new TeamModel(team_id, 2, "Juventus");
        teamService.save(team);

        Optional<TeamModel> fetchedTeam = teamService.findById(team_id);
        assertTrue(fetchedTeam.isPresent());
        assertEquals(team_id, fetchedTeam.get().getTeam_id());
    }

    @Test
    void findAll() {
    }

     */
}