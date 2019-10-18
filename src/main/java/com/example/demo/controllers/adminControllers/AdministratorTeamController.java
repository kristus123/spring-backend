package com.example.demo.controllers.adminControllers;

import com.example.demo.dtos.TeamDTO;
import com.example.demo.exceptions.InvalidTeamRequestException;
import com.example.demo.exceptions.TeamNotFoundException;
import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorTeamController {

    @Autowired
    private TeamService teamService;


    @PostMapping("/post/team")
    public TeamModel newTeam(@RequestBody TeamDTO team) {
        return teamService.createTeam(team);
    }

    @PutMapping("/update/team/{id}")
    public TeamModel updateTeam(@PathVariable Integer id, @RequestBody TeamModel teamModel) {
        if (id != teamModel.getTeamId()) {
            return null;
        }
        if (!teamService.findById(id).isPresent()) {
            return null;
        }

        return teamService.save(teamModel);
    }

    @DeleteMapping("/delete/team/{id}")
    public TeamModel deleteTeam(@PathVariable Integer id) {
        Optional<TeamModel> team = teamService.findById(id);
        if (!team.isPresent()) {
            return null;
        }

        teamService.deleteById(id);

        return team.get();
    }
}
