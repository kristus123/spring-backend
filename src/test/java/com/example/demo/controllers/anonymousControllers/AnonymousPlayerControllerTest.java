package com.example.demo.controllers.anonymousControllers;

import com.example.demo.controllers.AnonymousControllers.AnonymousPlayerController;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.boot.test.context.SpringBootTest;

import org.springframework.test.context.junit.jupiter.SpringExtension;

//Browse through player data with limited features shown

@ExtendWith (SpringExtension.class)
@SpringBootTest
public class AnonymousPlayerControllerTest {

    @Autowired
    AnonymousPlayerController anonymousPlayerController;

    @Test
    void onePlayer() {
System.out.println("Anonymous: Show one player's limited stats (name/team) - test");
    }
}
