package com.example.demo.controllers;

import com.example.demo.models.UserModel;
import com.example.demo.services.UserService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.net.URISyntaxException;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserManagementControllerTest {

    @Autowired
    UserService userService;

    @Autowired
    UserManagementController userManagementController;

    @Test
    void elevateUser() {

    }

    @Test
    void updateInfo() {
        /*
        String username = "Panda";
        String updatedUsername = "Shifu";
        String password = "superhemmelig123";

        UserModel user = userService.signup(username, password);
        user.setUsername(updatedUsername);

        // TODO PANDA: not looking good
        try {
            userManagementController.updateInfo(username, user);
        } catch (URISyntaxException ex) {
            ex.printStackTrace();
        }
        UserModel updatedUser = userService.findByUsername(updatedUsername).get();

        assertEquals(user.getUser_id(), updatedUser.getUser_id());
        assertEquals(updatedUsername, updatedUser.getUsername());

         */
    }
}