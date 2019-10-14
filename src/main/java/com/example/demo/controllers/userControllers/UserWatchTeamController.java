package com.example.demo.controllers.userControllers;

import com.example.demo.models.TeamModel;
import com.example.demo.models.UserModel;
import com.example.demo.services.TeamService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/v1/user/watchlist")
public class UserWatchTeamController {

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;

    @GetMapping("/get/team/{id}")
    TeamModel getTeam(@PathVariable Integer id) {
        UserModel user = userService.getUser();
        Optional<TeamModel> team = user.getTeams().stream().filter(t -> t.getTeamId() == id).findFirst();
        if (team.isPresent())
            return team.get();
        return null;
    }

    @GetMapping("/get/team")
    Set<TeamModel> getTeams() {
        UserModel user = userService.getUser();
        return user.getTeams();
    }

    @PostMapping("/post/team")
    TeamModel addTeam(@RequestBody TeamModel team) {
        UserModel user = userService.getUser();
        if (user.getTeams().add(team))
            return team;
        return null;
    }

    // updating fav team equals to removing that team and replacing it with (adding) another team...
    @PutMapping("/update/team/{id}")
    TeamModel updateTeam(@PathVariable Integer id, @RequestBody Integer otherId) {

        if (otherId == id)
            return null;

        Optional<TeamModel> existingTeam = teamService.findById(id);
        if (!existingTeam.isPresent())
            return null;

        Optional<TeamModel> otherTeam = teamService.findById(otherId);
        if (!otherTeam.isPresent())
            return null;

        UserModel user = userService.getUser();
        Set<TeamModel> teams = user.getTeams();
        if (teams.contains(existingTeam)) {
            teams.remove(existingTeam);
        }

        teams.add(otherTeam.get());
        return otherTeam.get();
    }

    @DeleteMapping("/delete/team/{id}")
    TeamModel deleteTeam(@PathVariable Integer id) {

        Optional<TeamModel> existingTeam = teamService.findById(id);
        if (!existingTeam.isPresent())
            return null;

        UserModel user = userService.getUser();
        if (!user.getTeams().remove(existingTeam.get()))
            return null;

        return existingTeam.get();
    }


}
