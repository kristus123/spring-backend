package com.example.demo.assembler;

import com.example.demo.controllers.SeasonController;
import com.example.demo.models.SeasonModel;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class SeasonResourceAssembler implements ResourceAssembler<SeasonModel, Resource<SeasonModel>> {

    @Override
    public Resource<SeasonModel> toResource(SeasonModel seasonModel) {
        return new Resource<>(seasonModel,
                linkTo(methodOn(SeasonController.class).oneSeason(seasonModel.getSeason_id())).withSelfRel(),
                linkTo(methodOn(SeasonController.class).allSeasons()).withRel("seasons"));
    }
}
