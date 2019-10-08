package com.example.demo.assembler;

import com.example.demo.models.PlayerModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class PlayerResourceAssembler implements ResourceAssembler<PlayerModel, Resource<PlayerModel>> {

    @Override
    public Resource<PlayerModel> toResource(PlayerModel playerModel) {
        return new Resource<>(playerModel);
    }
}
