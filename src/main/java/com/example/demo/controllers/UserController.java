package com.example.demo.controllers;


import com.example.demo.models.UserModel;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.Map;

@RestController
@RequestMapping("/v1/users")
public class UserController {
    @Autowired
    UserService userService;

    @PostMapping("/signup")
    public UserModel signup(@RequestBody Map<String, String> req) {
        return userService.signup(req.get("username") , req.get("password"));
    }



    @GetMapping("/me")
    public UserModel getMe(Principal principal) {
        if (principal == null) return null;

        return userService.findByUsername(principal.getName()).get();
    }

}
