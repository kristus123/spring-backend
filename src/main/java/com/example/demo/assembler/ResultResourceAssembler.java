package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonResultController;
import com.example.demo.controllers.commonControllers.CommonTeamController;
import com.example.demo.models.ResultModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class ResultResourceAssembler implements ResourceAssembler<ResultModel, Resource<ResultModel>> {

    @Override
    public Resource<ResultModel> toResource(ResultModel result) {
        return new Resource<>(result,
                linkTo(methodOn(CommonResultController.class).getResult(result.getMatchId())).withSelfRel(),
                linkTo(methodOn(CommonResultController.class).getResults()).withRel("results"),
                linkTo(methodOn(CommonTeamController.class).getTeam(result.getTeam().getTeamId())).withRel("team")
        );
    }
}