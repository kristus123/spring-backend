package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.CoachResourceAssembler;
import com.example.demo.dtos.CoachDTO;
import com.example.demo.models.CoachModel;
import com.example.demo.services.CoachService;
import com.example.demo.services.HumanService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin/")
public class AdministratorCoachController {

    @Autowired
    CoachService coachService;

    @Autowired
    CoachResourceAssembler assembler;

    @PostMapping("/post/coach")
    public ResponseEntity<Resource<CoachModel>> createCoach(@RequestBody CoachDTO coach) throws URISyntaxException {

        CoachModel coachModel = coachService.create(coach);
        Resource<CoachModel> resource = assembler.toResource(coachModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/coach/{id}")
    public ResponseEntity<Resource> updateCoach(@PathVariable Integer id, @RequestBody CoachDTO coach) throws URISyntaxException {

        CoachModel updated = coachService.update(id, coach);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/coach/{id}")
    public ResponseEntity<CoachModel> deleteCoach(@PathVariable Integer id) {
        CoachModel coach = coachService.deleteById(id);
        return ResponseEntity.ok(coach);
    }
}
