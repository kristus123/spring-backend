package com.example.demo.controllers.adminControllers;

import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

@RestController @RequestMapping("/v1/admin")
public class AdministratorController {
    @Autowired
    UserService userService;

    @PutMapping("/giveUserAdmin/{userId}")
    public boolean elevateUserToAdmin(@PathVariable int userId) {
        return userService.elevateUserToAdmin(userId);
    }

    @PutMapping("/giveUserStandard/{userId}")
    public boolean elevateUserToStandard(@PathVariable int userId) {
        return userService.elevateUserToStandard(userId);
    }



}
