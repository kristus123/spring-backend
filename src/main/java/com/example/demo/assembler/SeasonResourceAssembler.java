package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonSeasonController;
import com.example.demo.models.SeasonModel;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class SeasonResourceAssembler implements ResourceAssembler<SeasonModel, Resource<SeasonModel>> {

    @Override
    public Resource<SeasonModel> toResource(SeasonModel season) {
        return new Resource<>(season,
                linkTo(methodOn(CommonSeasonController.class).getSeason(season.getSeasonId())).withSelfRel(),
                linkTo(methodOn(CommonSeasonController.class).getSeasons()).withRel("seasons")
        );
    }
}
