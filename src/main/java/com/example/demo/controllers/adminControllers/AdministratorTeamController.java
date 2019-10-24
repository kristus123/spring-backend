package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.TeamResourceAssembler;
import com.example.demo.dtos.TeamDTO;
import com.example.demo.exceptions.ElementBadRequestException;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import org.json.JSONObject;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorTeamController {

    @Autowired
    TeamService teamService;

    @Autowired
    TeamResourceAssembler assembler;


    @PostMapping("/post/team")
    public ResponseEntity<Resource<TeamModel>> addTeam(@RequestBody TeamDTO team) throws URISyntaxException {
        /*
        if (team == null)
            throw new ElementBadRequestException("Empty JSON object provided");
         */

        TeamModel teamModel = teamService.create(team);
        Resource<TeamModel> resource = assembler.toResource(teamModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }


    //Ikke en så god convention. lar den være for nå lolololl lalaLALALALALSLASDALSDLASLDALSDLASKDASDKLASKLDSDJKLFDJKLSFGDKLFGHDFGHFJKL
    @PutMapping("/put/team/{teamId}/removeCoach")
    public TeamModel removeCoachFromTeam(@PathVariable int teamId) {
        TeamModel team = teamService.findById(teamId).orElseThrow(() -> new RuntimeException("team not found"));
        team.setCoach(null);
        teamService.save(team);
        return team;
    }

    //Dette er en bedre convention enn den over
    @PutMapping("/put/team/{teamId}/removeOwner")
    public boolean removeOwnerFromTeam(@PathVariable int teamId) {
        TeamModel team = teamService.findById(teamId).orElseThrow(() -> new ElementNotFoundException("ESUPER ERROR AAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAAA"));
        team.setOwner(null);
        teamService.save(team);



        return true;
    }



    @GetMapping("/get/team/with-no-owner")
    public List<TeamModel> getTeamWithNoOwner() {
        return teamService.findTeamsWithNoOwner();
    }


    @PutMapping("/update/team/{id}")
    public ResponseEntity<Resource> updateTeam(@PathVariable Integer id, @RequestBody TeamDTO team) throws URISyntaxException {
        /*
        if (team == null)
            throw new ElementBadRequestException("Empty JSON object provided");
         */

        TeamModel updated = teamService.update(id, team);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/team/{id}")
    public ResponseEntity<TeamModel> deleteTeam(@PathVariable Integer id) {
        TeamModel team = teamService.deleteById(id);
        return ResponseEntity.ok(team);
    }
}
