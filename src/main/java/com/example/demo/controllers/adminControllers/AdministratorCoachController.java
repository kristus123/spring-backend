package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.CoachResourceAssembler;
import com.example.demo.dtos.CoachDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.CoachModel;
import com.example.demo.models.TeamModel;
import com.example.demo.services.CoachService;
import com.example.demo.services.HumanService;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorCoachController {
    @Autowired
    TeamService teamService;
    @Autowired
    CoachService coachService;

    @Autowired
    CoachResourceAssembler assembler;

    @PostMapping("/post/coach")
    public CoachModel createCoach(@RequestBody CoachDTO coach) throws URISyntaxException {

        CoachModel coachModel = coachService.makePersonCoach(coach.getPersonId());
        //TeamModel team = teamService.findById(body.get("teamId")).orElseThrow(() -> new ElementNotFoundException("team not found"));
        //team.setCoach(coachModel);
        //teamService.save(team);
        return coachModel;
    }

    @PutMapping("/update/coach/{id}")
    public CoachModel updateCoach(@PathVariable Integer id, @RequestBody CoachDTO coach) throws URISyntaxException {
        //CoachModel c = coachService.findById(id).orElseThrow(() -> new ElementNotFoundException("did not find coach"));

        /*TeamModel team = teamService.findById(Integer.valueOf(response.get("newTeamId"))).orElseThrow(() -> new ElementNotFoundException("did not find"));
        team.setCoach(null);
        teamService.save(team);
        team = teamService.findById( Integer.valueOf(response.get("newTeamId")) ).orElseThrow(() -> new ElementNotFoundException("did not find the team that the new coach is supposed to play on"));
        team.setCoach(coach);
        teamService.save(team);*/
        CoachModel updatedCoach = coachService.update(id, coach);

        return updatedCoach; //team;

    }

    @DeleteMapping("/delete/coach/{id}")
    public ResponseEntity<CoachModel> deleteCoach(@PathVariable Integer id) {
        CoachModel coach = coachService.deleteById(id);
        return ResponseEntity.ok(coach);
    }
}
