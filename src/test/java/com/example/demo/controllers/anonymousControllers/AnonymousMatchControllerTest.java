package com.example.demo.controllers.anonymousControllers;

import com.example.demo.controllers.AnonymousControllers.AnonymousMatchController;
import com.example.demo.controllers.AnonymousControllers.AnonymousPlayerController;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.extension.ExtendWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

@ExtendWith (SpringExtension.class)
@SpringBootTest
public class AnonymousMatchControllerTest {

    @Autowired
    AnonymousMatchController anonymousMatchController;
    @Test
    void oneMatch() {
        System.out.println("Anonymous: Show one match win/lose/draw - test");
    }
}

