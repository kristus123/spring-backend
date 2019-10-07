package com.example.demo.controllers;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/user/manage")
public class UserManagementController {

    @GetMapping("/elevateUser/{userId}/{role}")
    public String elevateUser(int userId, String role) {
        return "elevated";
    }

}
