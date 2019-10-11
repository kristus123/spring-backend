package com.example.demo.controllers.adminControllers;

import com.example.demo.models.CoachModel;
import com.example.demo.services.CoachService;
import com.example.demo.services.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin/")
public class AdministratorCoachController {

    @Autowired
    private CoachService coachService;

    @GetMapping("/get/coach/{id}")
    public CoachModel getCoach(@PathVariable Integer id) {
        return coachService.findById(id).orElseGet(null);
    }

    @GetMapping("/get/coach")
    public List<CoachModel> getAllCoaches() {return coachService.findAll();}

    @PostMapping("/post/coach")
    public CoachModel createCoach(@RequestBody CoachModel coach) {
        return coachService.save(coach);
    }

    @PutMapping("/update/coach/{id}")
    public CoachModel updateCoach(@PathVariable Integer id, @RequestBody CoachModel coach) {
        return coachService.update(id, coach);
    }

    @Autowired
    HumanService humanService;

    @DeleteMapping("/delete/coach/{id}")
    public void deleteCoach(@PathVariable Integer id) {
        Optional<CoachModel> coach =  coachService.findById(id);
        if (coach.isPresent()) {
            humanService.delete(coach.get());
        }

    }
}
