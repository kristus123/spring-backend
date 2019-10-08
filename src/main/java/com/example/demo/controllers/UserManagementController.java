package com.example.demo.controllers;

import com.example.demo.assembler.UserResourceAssembler;
import com.example.demo.models.UserModel;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/api/v1/user/manage")
public class UserManagementController {

    @Autowired
    private UserService userService;

    @Autowired
    private UserResourceAssembler assembler;

    /*
    @GetMapping("/elevateUser/{userId}/{role}")
    public String elevateUser(@PathVariable Integer userId, @PathVariable String role) {
        return "elevated";
    }
     */

    @PostMapping("/update/{username}")
    public Resource<UserModel> updateInfo(@PathVariable String username, @RequestBody UserModel updatedUser) throws URISyntaxException {

        UserModel user = userService.findByUsername(username).get();
        if (user == null)
            throw new UsernameNotFoundException(username);

        user.setUsername(updatedUser.getUsername());
        user.setPassword(updatedUser.getPassword());

        userService.save(user);

        Resource<UserModel> resource = assembler.toResource(user);

        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */

        return resource;
    }



}
