package com.example.demo.controllers;


import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.security.Principal;

@RestController
@RequestMapping("/api/private")
public class PrivateController {

    @GetMapping
    public String getMessage(Principal principal) {
        return "Hello from private API controller " + principal.getName();
    }
}
