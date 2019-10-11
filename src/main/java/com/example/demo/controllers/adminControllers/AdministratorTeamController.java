package com.example.demo.controllers.adminControllers;

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
    public TeamModel addTeam(@RequestBody TeamModel teamModel) {
        return teamService.save(teamModel);
    }

    @PutMapping("/update/team/{id}")
    public TeamModel updateTeam(@PathVariable Integer id, @RequestBody TeamModel teamModel) {
        if (teamModel == null || teamModel.getTeamId() != id) {
            return null;
        }

        Optional<TeamModel> oldTeam = teamService.findById(id);
        if (!oldTeam.isPresent()) {
            return null;
        }

        return teamService.update(teamModel, oldTeam.get());
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
