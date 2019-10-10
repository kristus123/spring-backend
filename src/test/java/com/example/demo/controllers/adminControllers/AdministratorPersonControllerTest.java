package com.example.demo.controllers.adminControllers;

import com.example.demo.exceptions.PersonNotFoundException;
import org.assertj.core.api.Assertions;
import org.junit.Before;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.assertj.core.api.Assertions.*;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdministratorPersonControllerTest {

    @Autowired
    MockMvc mockMvc;

    public static int ID;

    @BeforeAll
    static void setUp() {
        ID = 1;
    }


    @Test
    void testThatCanGetPersonAfterPost() throws Exception {
        String json = "{\"firstName\":\"haadasdaskon\", \"lastName\":\"underdal\", \"dateOfBirth\":\"1994-05-01\"}";

        mockMvc.perform(post("/v1/admin/post/person").contentType(MediaType.APPLICATION_JSON).
                content(json)).
                andExpect(status().isOk());

        mockMvc.perform(get("/v1/admin/get/person/" + ID++)).andExpect(content().json(json));
    }

    /*
    * There is no easy way to capture nested exceptions
    */
    @Test
    void testThatExceptionIsThrownIfPersonDoesNotExist() throws PersonNotFoundException  {
        String personId = "10";
        Throwable t = Assertions.catchThrowable(() -> mockMvc.perform(get("/v1/admin/get/person/" + personId)));
        org.junit.jupiter.api.Assertions.assertEquals(
                "Request processing failed; nested exception is com.example.demo.exceptions.PersonNotFoundException: Could not find person with ID=" + personId,
                t.getMessage());
    }

    @Test
    void testThatPersonIsDeleted() throws Exception {
        String json = "{\"firstName\":\"haakdsadason\", \"lastName\":\"underdal\", \"dateOfBirth\":\"1994-05-01\"}";

        // Create person and check that it was an success
        mockMvc.perform(post("/v1/admin/post/person").contentType(MediaType.APPLICATION_JSON).
                content(json)).
                andExpect(content().json(json));

        // Delete person
        mockMvc.perform(delete("/v1/admin/delete/person/" + ID));

        // Check that person is not present
        String personId = "1";
        Throwable t = Assertions.catchThrowable(() -> mockMvc.perform(get("/v1/admin/get/person/" + ID++)));
        org.junit.jupiter.api.Assertions.assertEquals(
                "Request processing failed; nested exception is com.example.demo.exceptions.PersonNotFoundException: Could not find person with ID=" + personId,
                t.getMessage());
    }

    @Test
    void testThatCanGetAllPersons() throws Exception {
        String json = "{\"firstName\":\"haakon\", \"lastName\":\"underdal\", \"dateOfBirth\":\"1994-05-01\"}";
        mockMvc.perform(post("/v1/admin/post/person").contentType(MediaType.APPLICATION_JSON).
                content(json)).
                andExpect(status().isOk());
        ID++;
        mockMvc.perform(get("/v1/admin/get/person/")).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    void testThatPersonIsUpdated() throws Exception {
        String json = "{\"firstName\":\"haakon\", \"lastName\":\"underdal\", \"dateOfBirth\":\"1994-05-01\"}";
        String jsonUpdated = "{\"personId\":" + ID + ", \"firstName\":\"OLA\", \"lastName\":\"underdal\", \"dateOfBirth\":\"1994-05-01\"}";

        mockMvc.perform(post("/v1/admin/post/person").contentType(MediaType.APPLICATION_JSON).
                content(json)).
                andExpect(status().isOk());

        mockMvc.perform(put("/v1/admin/update/person/" + ID).contentType(MediaType.APPLICATION_JSON).
                content(jsonUpdated));

        mockMvc.perform(get("/v1/admin/get/person/" + ID++)).andExpect(content().json(jsonUpdated));
    }
}