package com.example.demo.assembler;

import com.example.demo.models.TeamModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class TeamResourceAssembler implements ResourceAssembler<TeamModel, Resource<TeamModel>> {

    @Override
    public Resource<TeamModel> toResource(TeamModel teamModel) {
        return new Resource<>(teamModel);
    }
}
