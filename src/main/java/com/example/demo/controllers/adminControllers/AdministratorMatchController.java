package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.MatchResourceAssembler;
import com.example.demo.dtos.MatchDTO;
import com.example.demo.models.MatchModel;
import com.example.demo.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorMatchController {


    @Autowired
    MatchService matchService;

    @Autowired
    MatchResourceAssembler assembler;

    @PostMapping("/post/match")
    public ResponseEntity<Resource<MatchModel>> addMatch(@RequestBody MatchDTO match) throws URISyntaxException {

        MatchModel teamModel = matchService.create(match);
        Resource<MatchModel> resource = assembler.toResource(teamModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/match/{id}")
    public ResponseEntity<Resource> updateMatch(@PathVariable Integer id, @RequestBody MatchDTO match) throws URISyntaxException {

        MatchModel updated = matchService.update(id, match);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/match/{id}")
    public ResponseEntity<MatchModel> deleteMatch(@PathVariable Integer id) {
        MatchModel match = matchService.deleteById(id);
        return ResponseEntity.ok(match);
    }

}
