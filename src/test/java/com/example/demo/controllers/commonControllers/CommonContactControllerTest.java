package com.example.demo.controllers.commonControllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CommonContactControllerTest {

    @Autowired
    MockMvc mockMvc;

    String jsonBody;
    static int ID = 0;

    @BeforeEach
    void setUp() throws Exception {

        jsonBody = "{\"personId\":" + ID + ", \"contactType\":\"phone\", \"contactDetail\":\"41003239\"}";

        mockMvc.perform(post("/v1/admin/post/contact").contentType(MediaType.APPLICATION_JSON).
                content(jsonBody)).
                andExpect(status().isOk());
        ID++;
    }

    @Test
    public void testThatCanGetContactAfterPost() throws Exception {
        mockMvc.perform(get("/v1/common/get/contact/" + ID)).andExpect(content().json("{\"contactType\":\"phone\", \"contactDetail\":\"41003239\"}"));
    }

    @Test
    public void testThatCanGetAllContacts() throws Exception {
        mockMvc.perform(get("/v1/common/get/contact/")).andExpect(jsonPath("$", hasSize(1)));
    }
}