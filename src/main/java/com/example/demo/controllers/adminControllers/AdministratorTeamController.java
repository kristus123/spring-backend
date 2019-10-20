package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.TeamResourceAssembler;
import com.example.demo.dtos.TeamDTO;
import com.example.demo.exceptions.ElementBadRequestException;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorTeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    TeamResourceAssembler assembler;


    @PostMapping("/post/team")
    public ResponseEntity<Resource<TeamModel>> addTeam(@RequestBody TeamDTO team) throws URISyntaxException {

        TeamModel teamModel = teamService.save(team);
        if (teamModel == null)
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        Resource<TeamModel> resource = assembler.toResource(teamModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/team/{id}")
    public ResponseEntity<Resource> updateTeam(@PathVariable Integer id, @RequestBody TeamDTO team) throws URISyntaxException {
        if (team == null)
            throw new ElementBadRequestException("Empty JSON object provided");
        if (team.getTeamId() != id)
            throw new ElementBadRequestException("Mismatch in pathID and ID within JSON object");

        TeamModel oldTeam = teamService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find team with ID=" + id));

        TeamModel updated = teamService.update(team, oldTeam);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/team/{id}")
    public ResponseEntity<TeamModel> deleteTeam(@PathVariable Integer id) {
        TeamModel team = teamService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find team with ID=" + id));

        teamService.deleteById(id);

        return ResponseEntity.ok(team);
    }
}
