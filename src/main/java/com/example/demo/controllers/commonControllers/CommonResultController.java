package com.example.demo.controllers.commonControllers;

import com.example.demo.assembler.ResultResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.ResultModel;
import com.example.demo.services.ResultService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/common")
public class CommonResultController {

    @Autowired
    ResultService resultService;

    @Autowired
    ResultResourceAssembler assembler;

    @GetMapping("/get/result/{id}")
    public ResponseEntity<Resource<ResultModel>> getResult(@PathVariable Integer id) {

        ResultModel team = resultService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find result with ID=" + id));

        Resource<ResultModel> resource = assembler.toResource(team);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/result")
    public ResponseEntity<Resources<Resource<ResultModel>>> getResults() {

        List<Resource<ResultModel>> results = resultService.findAll()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        // TODO PANDA: throw exception or return ResponseEntity.ok()?
        if (results.isEmpty())
            throw new ElementNotFoundException("No results registered");

        return ResponseEntity
                .ok(new Resources<>(results,
                        linkTo(methodOn(CommonResultController.class).getResults()).withSelfRel()));
    }


}
