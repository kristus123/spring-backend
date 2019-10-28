package com.example.demo.services;

import com.example.demo.dtos.TeamDTO;
import com.example.demo.exceptions.ElementNotFoundException;
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
    //@Test
    void testThatTeamIsSaved() {

        TeamModel saved = teamService.create(new TeamDTO(2, 1, 1, 1));

        assertNotNull(saved);
        assertEquals(2, saved.getAssociation().getAssociationId());
        assertEquals("Real Madrid", saved.getAssociation().getName());
    }

    //@Test
    void testThatSaveFailsForFaultyInput() {

        assertThrows(ElementNotFoundException.class, () ->
                teamService.create(new TeamDTO(-1, 1, 1, 1))
        );
    }

    //@Test
    void testThatTeamIsUpdated() {

        TeamModel saved = teamService.create(new TeamDTO(2, 1, 1, 1));
        TeamModel updated = teamService.update(saved.getTeamId(), new TeamDTO(2, 1, 1, 1));

        assertNotNull(updated);
        assertEquals(2, updated.getAssociation().getAssociationId());
        assertEquals("Real Madrid", updated.getAssociation().getName());
    }

    //@Test
    void testThatUpdateFailsForNonExistingTeam() {

        assertThrows(ElementNotFoundException.class, () ->
            teamService.update(-1, new TeamDTO(2, 1, 1, 1))
        );
    }


    //@Test
    void testThatTeamIsDeleted() {

        TeamModel saved = teamService.create(new TeamDTO(2, 1, 1, 1));
        TeamModel deleted = teamService.deleteById(saved.getTeamId());

        assertNotNull(deleted);
        assertFalse(deleted.isActive());
    }

    //@Test
    void testThatDeleteFailsForNonExistingTeam() {

        assertThrows(ElementNotFoundException.class, () ->
                teamService.deleteById(-1)
        );
    }

    //@Test
    void testThatTeamIsFound() {

        TeamModel saved = teamService.create(new TeamDTO(2, 1, 1, 1));
        Optional<TeamModel> found = teamService.findById(saved.getTeamId());

        assertTrue(found.isPresent());
        assertEquals(saved.getAssociation().getName(), found.get().getAssociation().getName());
    }


    //@Test
    void testThatFindFailsForNonExistingTeam() {

        Optional<TeamModel> found = teamService.findById(-1);
        assertFalse(found.isPresent());
    }

    //@Test
    void testThatTeamIsFoundAfterDeletion() {
        TeamModel saved = teamService.create(new TeamDTO(2, 1, 1, 1));
        TeamModel deleted = teamService.deleteById(saved.getTeamId());

        Optional<TeamModel> found = teamService.findByIdForced(deleted.getTeamId());
        assertTrue(found.isPresent());
        assertEquals(deleted.getAssociation().getName(), found.get().getAssociation().getName());
    }

    //@Test
    void testThatAllTeamsAreFound() {

        List<TeamModel> teams = teamService.findAllActive();
        assertFalse(teams.isEmpty());
    }

    //@Test
    void testThatFindAllFailsForNonExistingTeams() {

        teamService.findAllActive().forEach(team -> teamService.deleteById(team.getTeamId()));

        List<TeamModel> teams = teamService.findAllActive();
        assertTrue(teams.isEmpty());
    }

    //@Test
    void testThatAllTeamsAreFoundAfterDeletion() {

        teamService.findAllActive().forEach(team -> teamService.deleteById(team.getTeamId()));
        List<TeamModel> teams = teamService.findAllForced();

        assertFalse(teams.isEmpty());
    }
}