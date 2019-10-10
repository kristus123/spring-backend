package com.example.demo.controllers;


import com.example.demo.models.UserModel;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public UserModel signup(@RequestParam String username, @RequestParam String password ) {
        return userService.signup(username, password);
    }

    @GetMapping("/me")
    public UserModel getMe(Principal principal) {
        return userService.findByUsername(principal.getName()).get();
    }

}
