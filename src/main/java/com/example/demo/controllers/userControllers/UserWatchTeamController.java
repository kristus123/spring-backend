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
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
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
    public Resource<TeamModel> getTeam(@PathVariable Integer id, Principal principal) {

        UserModel user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ElementNotFoundException("Could not find user with username=" + principal.getName()));

        TeamModel team = teamService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find team with ID=" + id));

        if (!user.getTeams().contains(team))
            throw new ElementNotFoundException("Team with ID=" + id + " is not present in watchlist for user with username=" + principal.getName());

        assembler.setPrincipal(principal);
        return assembler.toResource(team);
    }


    @GetMapping("/get/team")
    public Resources<Resource<TeamModel>> getTeams(Principal principal) {

        UserModel user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ElementNotFoundException("Could not find user with username=" + principal.getName()));

        List<Resource<TeamModel>> teams = user.getTeams()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        return new Resources<>(teams,
                linkTo(methodOn(UserWatchTeamController.class).getTeams(principal)).withSelfRel());
    }

    @PostMapping("/post/team")
    ResponseEntity<?> addTeam(@RequestBody UserTeamDTO dto, Principal principal) throws URISyntaxException {

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

    /*
    // updating fav team equals to removing that team and replacing it with (adding) another team...
    // doesn't make sense to allow this operation for a User
    @PutMapping("/update/team/{id}")
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

     */

    @DeleteMapping("/delete/team/{id}")
    ResponseEntity<?> deleteTeam(@PathVariable Integer id, Principal principal) {

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
