package com.example.demo.controllers.userControllers;

import com.example.demo.exceptions.TeamNotFoundException;
import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/user")
public class UserTeamController {

    @Autowired
    private TeamService teamService;

    @GetMapping("/get/team/{id}")
    public TeamModel oneTeam(@PathVariable Integer id) {

        Optional<TeamModel> team = teamService.findById(id);
        if(!team.isPresent())
            return null;

        return team.get();
    }

    @GetMapping("/get/team")
    public List<TeamModel> allTeams() {

        List<TeamModel> teams = teamService.findAll();

        return teams;
    }
}
