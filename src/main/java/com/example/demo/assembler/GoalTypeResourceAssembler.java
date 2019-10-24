package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonGoalTypeController;
import com.example.demo.models.GoalTypeModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;


@Component
public class GoalTypeResourceAssembler implements ResourceAssembler<GoalTypeModel, Resource<GoalTypeModel>> {

    @Override
    public Resource<GoalTypeModel> toResource(GoalTypeModel goalType) {
        return new Resource<>(goalType,
                linkTo(methodOn(CommonGoalTypeController.class).getGoalType(goalType.getGoalTypeId())).withSelfRel(),
                linkTo(methodOn(CommonGoalTypeController.class).getGoalTypes()).withRel("goalTypes")
        );
    }

}