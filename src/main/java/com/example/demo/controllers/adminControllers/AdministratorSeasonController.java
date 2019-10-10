package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.SeasonResourceAssembler;
import com.example.demo.models.SeasonModel;
import com.example.demo.services.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorSeasonController {


    @Autowired
    private SeasonService seasonService;
    @Autowired
    private SeasonResourceAssembler seasonResourceAssembler;


    @PostMapping("/post/season")
    public Resource<SeasonModel> newSeason(@RequestBody SeasonModel seasonModel) throws URISyntaxException {

        Resource<SeasonModel> resource = seasonResourceAssembler.toResource(seasonService.save(seasonModel));
        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */

        return resource;
    }

    @PutMapping("/update/season/{id}")
    public Resource<SeasonModel> updateMatch(@PathVariable Integer id, @RequestBody SeasonModel seasonModel) {
        if (seasonModel.getSeasonId() != id || !seasonService.findById(id).isPresent()) {
            //ResponseEntity.badRequest().build();
            return null;
        }

        //return ResponseEntity.ok(associationService.save(associationModel));
        return seasonResourceAssembler.toResource(seasonService.save(seasonModel));
    }

    @DeleteMapping("/delete/season/{id}")
    public Resource<SeasonModel> deleteMatch(@PathVariable Integer id) {
        Optional<SeasonModel> season = seasonService.findById(id);
        if (!season.isPresent()) {
            //ResponseEntity.badRequest().build();
            return null;
        }

        seasonService.deleteById(id);

        //return ResponseEntity.ok().build();
        return seasonResourceAssembler.toResource(season.get());
    }
}
