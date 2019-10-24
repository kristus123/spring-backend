package com.example.demo.controllers.adminControllers;

import com.example.demo.dtos.*;
import com.example.demo.LifeHack;
import com.example.demo.enums.ContactType;
import com.example.demo.models.*;
import com.example.demo.services.*;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.time.Month;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdministratorTeamControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired AddressService addressService;

    @Autowired LocationService locationService;

    @Autowired PersonService personService;

    @Autowired ContactService contactService;

    @Autowired CoachService coachService;

    @Autowired OwnerService ownerService;

    @Autowired AssociationService associationService;

    @Autowired TeamService teamService;

    @Autowired PlayerService playerService;

    @Autowired SeasonService seasonService;

    @Autowired MatchService matchService;

    @Autowired MatchGoalService matchGoalService;

    @Autowired GoalTypeService goalTypeService;





    //@BeforeEach
    void setup() {

        //Lag en standard person med
        AddressModel address = addressService.createAddress(new AddressModel("5306", "Erdal", "Norway", "Vestre 30"));
        LocationModel location = locationService.save(new LocationModel(address, "Macnhester stadium", "it's in Kristian's backyard"));

        PersonModel person = personService.create(new PersonModel("Kristian", "Solbakken", LocalDate.of(2018, Month.FEBRUARY, 1), address));
        contactService.create(new ContactDTO(person.getPersonId(), ContactType.EMAIL, "panda@panda.com"));

        CoachModel coach = coachService.create(new CoachDTO(person.getPersonId()));
        OwnerModel owner = ownerService.create(new OwnerDTO(person.getPersonId()));

        AssociationModel association = associationService.create(new AssociationModel("Manchester", "Best team"));
        TeamModel homeTeam = teamService.create(new TeamDTO(association.getAssociationId(), coach.getCoachId(), owner.getOwnerId(), location.getLocationId()));




        address = addressService.createAddress(new AddressModel("489489", "OSLO", "SWEDEN", "ve30"));
        location = locationService.save(new LocationModel(address, "Bislett stadion", "ved bislett kebab"));

        person =  personService.create(new PersonModel("Alex", "Johansen", LocalDate.of(2015, 2, 2), address));
        contactService.create(new ContactDTO(person.getPersonId(), ContactType.PHONE, "21212121"));

        PlayerModel player = playerService.create(new PlayerDTO(person.getPersonId(), homeTeam.getTeamId(), person.getFirstName()+" "+person.getLastName()));
        player.setNormalPosition("BACK");
        player.setPlayerNumber("25");
        playerService.save(player);

        association = associationService.create(new AssociationModel("Juventus", "Better than best team"));
        TeamModel awayTeam = teamService.create(new TeamDTO(association.getAssociationId(), coach.getCoachId(), owner.getOwnerId(), location.getLocationId()));

        person =  personService.create(new PersonModel("Ole", "Dole", LocalDate.of(2020, 2, 2), address));
        player = playerService.create(new PlayerDTO(person.getPersonId(), awayTeam.getTeamId(), person.getFirstName()+" "+person.getLastName()));

        SeasonModel season = seasonService.save(new SeasonModel(LocalDate.of(2000, Month.JANUARY, 1), LocalDate.of(2005, Month.JANUARY, 1), "S1E2", "good times"));
        MatchModel match = matchService.create(new MatchDTO(LocalDate.of(2019, Month.DECEMBER, 24), homeTeam.getTeamId(), awayTeam.getTeamId(), season.getSeasonId(), location.getLocationId()));

        GoalTypeModel goalType = goalTypeService.save(new GoalTypeModel("SCORPION_KICK"));

        MatchGoalModel goal = matchGoalService.create(new MatchGoalDTO(player.getPlayerId(), goalType.getGoalTypeId() , match.getMatchId()));
        System.out.println(
                "goal="+goal.getGoalId()+
                        "\nplayer="+goal.getPlayer().getPlayerId()+
                        "\nmatch="+goal.getMatch().getMatchId());

    }

    //@AfterEach
    void destroy() {
        addressService.deleteAll();
    }


    //@Test
    void testThatTeamIsCreated() throws Exception {

        Integer id = 1;
        TeamDTO team = new TeamDTO(1, 1, 1, 1);

        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    void testThatCreateFailsForFaultyInput() throws Exception {
        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(null))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }

    //@Test
    void testThatTeamIsUpdated() throws Exception {

        Integer id = 1;
        TeamDTO team = new TeamDTO(2, 1, 1, 1);

        mockMvc.perform(put("/v1/admin/update/team/{id}", id)
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));
    }

    //@Test
    void testThatUpdateFailsForNonExistingTeam() throws Exception {

        Integer pathId = -1;
        TeamDTO team = new TeamDTO(1, 1, 1, 1);

        mockMvc.perform(put("/v1/admin/update/team/{id}", pathId)
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound());
    }

    void testThatUpdateFailsForFaultyInput() throws Exception {
        mockMvc.perform(put("/v1/admin/update/team/{id}", 1)
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(""))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isBadRequest());
    }


    //@Test
    void testThatTeamIsDeleted() throws Exception {

        TeamDTO team = new TeamDTO(1, 1, 1, 1);

        mockMvc.perform(post("/v1/admin/post/team")
                .contentType(MediaType.APPLICATION_JSON)
                .content(LifeHack.asJsonString(team))
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isCreated())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8));

        mockMvc.perform(delete("/v1/admin/delete/team/{id}", 1))
                .andExpect(status().isOk());
    }

    //@Test
    void testThatDeleteFailsForNonExistingTeam() throws Exception {
        mockMvc.perform(delete("/v1/admin/delete/team/{id}", -1))
                .andExpect(status().isNotFound());
    }

}