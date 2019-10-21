package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.SeasonResourceAssembler;
import com.example.demo.models.SeasonModel;
import com.example.demo.services.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorSeasonController {


    @Autowired
    SeasonService seasonService;

    @Autowired
    SeasonResourceAssembler assembler;


    @PostMapping("/post/season")
    public ResponseEntity<Resource<SeasonModel>> addSeason(@RequestBody SeasonModel season) throws URISyntaxException {

        SeasonModel teamModel = seasonService.save(season);
        Resource<SeasonModel> resource = assembler.toResource(teamModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/season/{id}")
    public ResponseEntity<Resource> updateMatch(@PathVariable Integer id, @RequestBody SeasonModel season) throws URISyntaxException {

        SeasonModel updated = seasonService.update(id, season);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/season/{id}")
    public ResponseEntity<SeasonModel> deleteMatch(@PathVariable Integer id) {
        SeasonModel season = seasonService.deleteById(id);
        return ResponseEntity.ok(season);
    }
}
