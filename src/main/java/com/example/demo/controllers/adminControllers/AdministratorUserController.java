package com.example.demo.controllers.adminControllers;

import com.example.demo.models.UserModel;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController @RequestMapping("/v1/admin")
public class AdministratorUserController {

    @Autowired UserService userService;

    @DeleteMapping("/delete/user/{userId}")
    public boolean delete(@PathVariable int userId) {
        UserModel user = userService.findById(userId);
        userService.delete(user);
        return true;
    }
}
