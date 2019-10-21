package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.MatchGoalResourceAssembler;
import com.example.demo.dtos.MatchGoalDTO;
import com.example.demo.models.MatchGoalModel;
import com.example.demo.services.MatchGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorMatchGoalController {

    @Autowired
    MatchGoalService matchGoalService;

    @Autowired
    MatchGoalResourceAssembler assembler;


    @GetMapping("/get/matchgoal/{matchId}/byMatchId")
    public List<MatchGoalModel> getMatchGoalsGivenByMatchId(@PathVariable Integer matchId) {
        return matchGoalService.findByMatchId(matchId);
    }

    @PostMapping("/post/matchgoal")
    public ResponseEntity<Resource<MatchGoalModel>> createMatchGoal(@RequestBody MatchGoalDTO matchGoal) throws URISyntaxException {

        MatchGoalModel teamModel = matchGoalService.create(matchGoal);
        Resource<MatchGoalModel> resource = assembler.toResource(teamModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/matchgoal/{id}")
    public ResponseEntity<Resource> updateMatchGoal(@PathVariable Integer id, @RequestBody MatchGoalDTO matchGoal) throws URISyntaxException {

        MatchGoalModel updated = matchGoalService.update(id, matchGoal);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/matchgoal/{id}")
    public ResponseEntity<MatchGoalModel> deleteMatchGoal(@PathVariable Integer id) {
        MatchGoalModel matchGoal = matchGoalService.deleteById(id);
        return ResponseEntity.ok(matchGoal);
    }
}
