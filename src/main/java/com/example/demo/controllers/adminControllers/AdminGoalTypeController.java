package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.GoalTypeResourceAssembler;
import com.example.demo.models.GoalTypeModel;
import com.example.demo.services.GoalTypeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController
@RequestMapping("/v1/admin")
public class AdminGoalTypeController {

    @Autowired
    GoalTypeService goalTypeService;

    @Autowired
    GoalTypeResourceAssembler assembler;

    @PostMapping("/post/goaltype")
    public ResponseEntity<Resource<GoalTypeModel>> addGoalType(@RequestBody GoalTypeModel goalType) throws URISyntaxException {

        GoalTypeModel goalTypeModel = goalTypeService.save(goalType);
        Resource<GoalTypeModel> resource = assembler.toResource(goalTypeModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/goaltype/{id}")
    public ResponseEntity<Resource> updateGoalType(@PathVariable Integer id, @RequestBody GoalTypeModel goalType) throws URISyntaxException {

        GoalTypeModel updated = goalTypeService.update(id, goalType);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/goaltype/{id}")
    public ResponseEntity<GoalTypeModel> deleteGoalType(@PathVariable Integer id) {
        GoalTypeModel goalType = goalTypeService.deleteById(id);
        return ResponseEntity.ok(goalType);
    }

}
