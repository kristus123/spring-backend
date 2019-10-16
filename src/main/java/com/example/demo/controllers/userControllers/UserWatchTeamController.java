package com.example.demo.controllers.userControllers;

import com.example.demo.models.TeamModel;
import com.example.demo.models.UserModel;
import com.example.demo.services.TeamService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
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
    TeamModel getTeam(@PathVariable Integer id, Principal principal) {
        Optional<UserModel> user = userService.findByUsername(principal.getName());
        if (!user.isPresent())
            return null;

        Optional<TeamModel> team = teamService.findById(id);
        if (!team.isPresent())
            return null;

        if (!user.get().getTeams().contains(team.get()))
            return null;

        System.out.println("TEST: successfully retrieved team from watchlist");
        return team.get();
    }


    @GetMapping("/get/team")
    Set<TeamModel> getTeams(Principal principal) {
        Optional<UserModel> user = userService.findByUsername(principal.getName());
        if (!user.isPresent())
            return null;

        Set<TeamModel> teams = user.get().getTeams();
        if (teams.isEmpty())
            System.out.println("TEST: no teams found");
        else
            System.out.println("TEST: successfully retrieved teams from watchlist");

        return teams;
    }

    @PostMapping("/post/team")
    TeamModel addTeam(@RequestBody TeamModel team, Principal principal) {
        Optional<UserModel> user = userService.findByUsername(principal.getName());
        UserModel userModel = userService.getMe(principal);

        // User exists?
        if (!user.isPresent())
            return null;

        // Player exists?
        if (!teamService.findById(team.getTeamId()).isPresent()) {
            return null;
        }

        // Add player to watchlist
        if(!user.get().addTeam(team))
            return null;

        userService.save(user.get());
        teamService.save(team);

        System.out.println("TEST: teams in user");
        System.out.println(userModel.getTeams());
        return team;
    }


    // updating fav team equals to removing that team and replacing it with (adding) another team...
    // doesn't make sense to allow this operation for a User
    //@PutMapping("/update/team/{id}")
    TeamModel updateTeam(@PathVariable Integer id, @RequestBody TeamModel updatedTeam, Principal principal) {
        if (updatedTeam.getTeamId() != id)
            return null;

        // User exists?
        Optional<UserModel> user = userService.findByUsername(principal.getName());
        if (!user.isPresent())
            return null;

        // Players exist?

        if (updatedTeam == null)
            return null;

        Optional<TeamModel> existingTeam = teamService.findById(id);
        if (!existingTeam.isPresent())
            return null;

        if (!user.get().deleteTeam(existingTeam.get()))
            return null;

        if (!user.get().addTeam(updatedTeam))
            return null;

        userService.save(user.get());
        teamService.update(updatedTeam, existingTeam.get());

        System.out.println("TEST: updated team successfully");
        return updatedTeam;
    }

    @DeleteMapping("/delete/team/{id}")
    TeamModel deleteTeam(@PathVariable Integer id, Principal principal) {

        Optional<TeamModel> team = teamService.findById(id);
        if (!team.isPresent())
            return null;

        Optional<UserModel> user = userService.findByUsername(principal.getName());
        if (!user.isPresent())
            return null;

        if (!user.get().deleteTeam(team.get()))
            return null;

        userService.save(user.get());
        teamService.save(team.get());

        System.out.println("TEST: successfully deleted team from watchlist");

        return team.get();
    }

}
