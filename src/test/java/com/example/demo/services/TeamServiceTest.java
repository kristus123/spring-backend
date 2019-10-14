package com.example.demo.services;

import com.example.demo.models.TeamModel;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest
class TeamServiceTest {

    @Autowired
    TeamService teamService;

    /**
     * https://stackoverflow.com/questions/34793104/what-is-the-best-way-to-test-controllers-and-services-with-junit
     */
    @Test
    void save() {

        Integer id = 1;
        String name = "Juventus";
        TeamModel team = new TeamModel(id, id, name);

        TeamModel saved = teamService.save(team);
        assertNotNull(saved);
        assertEquals(id, saved.getTeamId());
        assertEquals(name, saved.getAssociation().getName());

    }

    @Test
    void updateTeam() {

        Integer id = 1;
        String name = "Juventus";
        TeamModel saved = teamService.save(new TeamModel(id, id, name));

        name = "ManU";
        TeamModel updated = teamService.update(new TeamModel(id, id, name), saved);
        assertNotNull(updated);
        assertEquals(id, updated.getTeamId());
        assertEquals(name, updated.getAssociation().getName());

    }

    @Test
    void updateWrongTeam() {

        Integer id = 1;
        String name = "Juventus";
        TeamModel saved = teamService.save(new TeamModel(id, id, name));

        name = "ManU";
        TeamModel updated = teamService.update(new TeamModel(2, id, name), saved);
        assertNull(updated);
    }

    @Test
    void updateEmptyTeam() {

    }

    @Test
    void updateNonExistingTeam() {

    }

    @Test
    void deleteTeam() {

        Integer id = 1;
        String name = "Juventus";
        TeamModel team = new TeamModel(id, id, name);
        teamService.save(team);

        teamService.delete(team);

        Optional<TeamModel> empty = teamService.findById(id);
        assertFalse(empty.isPresent());

    }

    @Test
    void deleteNonExistingTeam() {

    }

    @Test
    void deleteById() {

        Integer id = 1;
        String name = "Juventus";
        teamService.save(new TeamModel(id, id, name));

        teamService.deleteById(id);

        Optional<TeamModel> empty = teamService.findById(id);
        assertFalse(empty.isPresent());
    }

    @Test
    void findTeam() {

        Integer id = 1;
        String name = "Juventus";
        TeamModel team = new TeamModel(id, id, name);

        teamService.save(team);

        Optional<TeamModel> found = teamService.findById(id);
        assertTrue(found.isPresent());

    }


    @Test
    void findNonExistingTeam() {

        Integer id = 10;

        Optional<TeamModel> found = teamService.findById(id);
        assertFalse(found.isPresent());
    }


    @Test
    void findTeams() {

        Integer id = 1;
        String name = "Juventus";
        teamService.save(new TeamModel(id, id, name));

        List<TeamModel> teams = teamService.findAll();
        assertFalse(teams.isEmpty());

        TeamModel found = teams.get(0);
        assertEquals(id, found.getTeamId());
        assertEquals(name, found.getAssociation().getName());

    }

    @Test
    void findNonExistingTeams() {

        List<TeamModel> empty = teamService.findAll();
        assertTrue(empty.isEmpty());

    }
}