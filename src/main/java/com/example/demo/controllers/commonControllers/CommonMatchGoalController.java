package com.example.demo.controllers.commonControllers;

import com.example.demo.models.ContactModel;
import com.example.demo.models.MatchGoalModel;
import com.example.demo.services.MatchGoalService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;


@RestController
@RequestMapping("/v1/common")
public class CommonMatchGoalController {

    @Autowired
    MatchGoalService matchGoalService;

    @GetMapping("/get/matchgoal/{id}")
    public MatchGoalModel getMatchGoal(@PathVariable Integer id) {

        Optional<MatchGoalModel> matchGoal = matchGoalService.findById(id);
        if(!matchGoal.isPresent())
            return null;

        return matchGoal.get();
    }

    @GetMapping("/get/matchgoal")
    public List<MatchGoalModel> getMatchGoals() {

        List<MatchGoalModel> matchGoals = matchGoalService.findAll();

        if (matchGoals.isEmpty())
            return null;

        return matchGoals;
    }
}

