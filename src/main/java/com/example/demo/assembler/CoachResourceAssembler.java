package com.example.demo.assembler;

import com.example.demo.controllers.commonControllers.CommonCoachController;
import com.example.demo.controllers.commonControllers.CommonPersonController;
import com.example.demo.models.CoachModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.*;

@Component
public class CoachResourceAssembler implements ResourceAssembler<CoachModel, Resource<CoachModel>> {

    @Override
    public Resource<CoachModel> toResource(CoachModel coach) {
        return new Resource<>(coach,
                linkTo(methodOn(CommonCoachController.class).getCoach(coach.getCoachId())).withSelfRel(),
                linkTo(methodOn(CommonCoachController.class).getCoaches()).withRel("coaches"),
                linkTo(methodOn(CommonPersonController.class).getPerson(coach.getPerson().getPersonId())).withRel("person")
        );
    }
}