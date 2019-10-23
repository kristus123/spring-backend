package com.example.demo.controllers.commonControllers;

import com.example.demo.repositories.OwnerRepository;
import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CommonOwnerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OwnerRepository ownerRepository;



    @Test
    void getOwners() {
    }

    @Test
    void getOwner() throws Exception {
        JSONObject json = new JSONObject()
                .put("ownerId", 1)
                .put("person", new JSONObject());

        System.out.println("____________");
        mockMvc.perform(get("/v1/common/get/owner/1"))
                .andDo(print())
                .andExpect(content().json(json.toString()))
                .andExpect(status().isOk());

        System.out.println("____________");
    }
}