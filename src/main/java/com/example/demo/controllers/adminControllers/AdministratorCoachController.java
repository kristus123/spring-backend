package com.example.demo.controllers.adminControllers;

import com.example.demo.models.CoachModel;
import com.example.demo.services.CoachService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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

    @DeleteMapping("/delete/coach/{id}")
    public void deleteCoach(@PathVariable Integer id) {
        coachService.delete(id);
    }
}
