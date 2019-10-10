package com.example.demo.controllers.adminControllers;


import com.example.demo.assembler.MatchResourceAssembler;
import com.example.demo.models.MatchModel;
import com.example.demo.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorMatchController {


    @Autowired
    private MatchService matchService;
    @Autowired
    private MatchResourceAssembler matchResourceAssembler;


    @PostMapping("/post/match")
    public Resource<MatchModel> newMatch(@RequestBody MatchModel matchModel) throws URISyntaxException {

        Resource<MatchModel> resource = matchResourceAssembler.toResource(matchService.save(matchModel));
        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */

        return resource;
    }

    @PutMapping("/update/match/{id}")
    public Resource<MatchModel> updateMatch(@PathVariable Integer id, @RequestBody MatchModel matchModel) {
        if (matchModel.getMatchId() != id || !matchService.findById(id).isPresent()) {
            //ResponseEntity.badRequest().build();
            return null;
        }

        //return ResponseEntity.ok(associationService.save(associationModel));
        return matchResourceAssembler.toResource(matchService.save(matchModel));
    }

    @DeleteMapping("/delete/match/{id}")
    public Resource<MatchModel> deleteMatch(@PathVariable Integer id) {
        Optional<MatchModel> match = matchService.findById(id);
        if (!match.isPresent()) {
            //ResponseEntity.badRequest().build();
            return null;
        }

        matchService.deleteById(id);

        //return ResponseEntity.ok().build();
        return matchResourceAssembler.toResource(match.get());
    }
}
