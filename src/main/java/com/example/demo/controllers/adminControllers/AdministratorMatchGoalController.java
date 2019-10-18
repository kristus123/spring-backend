package com.example.demo.controllers.adminControllers;

import com.example.demo.models.MatchGoalModel;
import com.example.demo.services.MatchGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorMatchGoalController {

    @Autowired
    private MatchGoalService matchGoalService;

    @GetMapping("/get/matchgoal/{id}")
    public MatchGoalModel getMatchGoal(@PathVariable Integer id) {
        return matchGoalService.findById(id).orElseGet(null);
    }

    @GetMapping("/get/matchgoal")
    public List<MatchGoalModel> getAllMatchGoals() {
        return matchGoalService.findAll();
    }

    @GetMapping("/get/matchgoal/{matchId}/byMatchId")
    public List<MatchGoalModel> getMatchGoalsGivenByMatchId(@PathVariable Integer matchId) {
        return matchGoalService.findByMatchId(matchId);
    }

    @PostMapping("/post/matchgoal")
    public MatchGoalModel createMatchGoal(@RequestBody MatchGoalModel matchGoal) {
        return matchGoalService.save(matchGoal);
    }

    @PutMapping("/update/matchgoal/{id}")
    public MatchGoalModel updateMatchGoal(@PathVariable Integer id, @RequestBody MatchGoalModel matchGoal) {
        return matchGoalService.update(id, matchGoal);
    }

    @DeleteMapping("/delete/matchgoal/{id}")
    public void deleteMatchGoal(@PathVariable Integer id) {
        matchGoalService.delete(id);
    }
}
