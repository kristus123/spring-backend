package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.MatchGoalResourceAssembler;
import com.example.demo.assembler.MatchPositionResourceAssembler;
import com.example.demo.dtos.MatchPositionDTO;
import com.example.demo.models.MatchPositionId;
import com.example.demo.models.MatchPositionModel;
import com.example.demo.services.MatchPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorMatchPositionController {

    @Autowired
    MatchPositionService matchPositionService;

    @Autowired
    MatchPositionResourceAssembler assembler;


    @PostMapping("post/matchPosition")
    public ResponseEntity<Resource<MatchPositionModel>> addMatchPosition(@RequestBody MatchPositionDTO matchPosition) throws URISyntaxException {
        MatchPositionModel matchPositionModel = matchPositionService.create(matchPosition);
        Resource<MatchPositionModel> resource = assembler.toResource(matchPositionModel);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/matchPosition")
    public ResponseEntity<Resource> updateMatchPosition(@RequestBody MatchPositionDTO matchPosition) throws URISyntaxException {

        MatchPositionModel updated = matchPositionService.update(matchPosition);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/matchPosition")
    public ResponseEntity<MatchPositionModel> deleteMatchPosition(@RequestBody MatchPositionId id) {
        MatchPositionModel matchPosition = matchPositionService.deleteById(id);
        return ResponseEntity.ok(matchPosition);
    }
}
