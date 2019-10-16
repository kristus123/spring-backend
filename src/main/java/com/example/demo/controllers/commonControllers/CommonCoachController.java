package com.example.demo.controllers.commonControllers;

import com.example.demo.models.CoachModel;
import com.example.demo.services.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/common")
public class CommonCoachController {

    @Autowired
    CoachService coachService;

    @GetMapping("/get/coach/{id}")
    public CoachModel getCoach(@PathVariable Integer id) {

        Optional<CoachModel> coach = coachService.findById(id);
        if(!coach.isPresent())
            return null;

        return coach.get();
    }

    @GetMapping("/get/coach")
    public List<CoachModel> getCoaches() {

        List<CoachModel> coaches = coachService.findAll();

        if (coaches.isEmpty())
            return null;

        return coaches;
    }
}
