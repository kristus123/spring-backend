package com.example.demo.assembler;

import com.example.demo.models.UserModel;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.ResourceAssembler;
import org.springframework.stereotype.Component;

@Component
public class UserResourceAssembler implements ResourceAssembler<UserModel, Resource<UserModel>> {

    @Override
    public Resource<UserModel> toResource(UserModel userModel) {
        return new Resource<>(userModel); // TODO PANDA: add links later
    }
}
