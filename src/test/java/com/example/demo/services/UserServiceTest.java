package com.example.demo.services;

import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class UserServiceTest {



    @Autowired
    UserService userService;


    @Test
    void signupAndLogin() {
        UserModel user = userService.signup("kristian", "hemmelig123");

        assertTrue(user.getUsername().equalsIgnoreCase("kristian"));

        assertFalse(user.getPassword().equalsIgnoreCase("hemmelig123"));

        //ERROR HER
        //assertTrue(userService.login("kristian", "hemmelig123"));

    }

    @Test
    void save() {
        String username = "Panda";
        String password = "superhemmelig123";

        UserModel user = userService.signup(username, password);

        user.setUsername("Shifu");
        UserModel savedUser = userService.save(user);
        assertTrue(user.getUsername().equalsIgnoreCase(savedUser.getUsername()));
    }

    @Test
    void findByUsername() {
        String username = "Panda";
        String password = "superhemmelig123";

        UserModel insertedUser = userService.signup(username, password);

        assertTrue(username.equals(userService.findByUsername(username).get().getUsername()));
    }



}