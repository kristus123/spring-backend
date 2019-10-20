package com.example.demo.controllers.userControllers;

import com.example.demo.assembler.TeamResourceAssembler;
import com.example.demo.assembler.UserTeamResourceAssembler;
import com.example.demo.dtos.UserTeamDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.TeamModel;
import com.example.demo.models.UserModel;
import com.example.demo.services.TeamService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/user/watchlist")
public class UserWatchTeamController {

    @Autowired
    UserService userService;

    @Autowired
    TeamService teamService;

    @Autowired
    UserTeamResourceAssembler assembler;

    @GetMapping("/get/team/{id}")
    public ResponseEntity<Resource<TeamModel>> getTeam(@PathVariable Integer id, Principal principal) {

        UserModel user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ElementNotFoundException("Could not find user with username=" + principal.getName()));

        TeamModel team = teamService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find team with ID=" + id));

        if (!user.getTeams().contains(team))
            throw new ElementNotFoundException("Team with ID=" + id + " is not present in watchlist for user with username=" + principal.getName());

        assembler.setPrincipal(principal);
        Resource<TeamModel> resource = assembler.toResource(team);

        return ResponseEntity
                .ok(resource);
    }


    @GetMapping("/get/team")
    public ResponseEntity<Resources<Resource<TeamModel>>> getTeams(Principal principal) {

        UserModel user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ElementNotFoundException("Could not find user with username=" + principal.getName()));

        List<Resource<TeamModel>> teams = user.getTeams()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        if (teams.isEmpty())
            throw new ElementNotFoundException("No teams in watchlist for user with username=" + principal.getName());

        return ResponseEntity
                .ok(new Resources<>(teams,
                        linkTo(methodOn(UserWatchTeamController.class).getTeams(principal)).withSelfRel()));
    }

    @PostMapping("/post/team")
    public ResponseEntity<Resource<TeamModel>> addTeam(@RequestBody UserTeamDTO dto, Principal principal) throws URISyntaxException {

        // User exists?
        UserModel user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ElementNotFoundException("Could not find user with username=" + principal.getName()));

        // Team exists?
        TeamModel team = teamService.findById(dto.getTeamId())
                .orElseThrow(() -> new ElementNotFoundException("Team with ID=" + dto.getTeamId() + " does not exist"));

        // Add player to watchlist
        if(!user.addTeam(team))
            return null;

        userService.save(user);
        teamService.save(team);

        assembler.setPrincipal(principal);
        Resource<TeamModel> resource = assembler.toResource(team);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    // updating fav team equals to removing that team and replacing it with (adding) another team...
    // doesn't make sense to allow this operation for a User


    @DeleteMapping("/delete/team/{id}")
    public ResponseEntity<TeamModel> deleteTeam(@PathVariable Integer id, Principal principal) {

        // Team exists?
        TeamModel team = teamService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Team with ID=" + id + " does not exist"));

        // User exists?
        UserModel user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ElementNotFoundException("Could not find user with username=" + principal.getName()));


        if (!user.deleteTeam(team))
            return null;

        userService.save(user);
        teamService.save(team);

        return ResponseEntity.ok(team);
    }

}
