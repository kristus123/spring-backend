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

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


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
        String json = "{\"first_name\":\"haadasdaskon\", \"last_name\":\"underdal\", \"date_of_birth\":\"1994-05-01\"}";

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
        String person_id = "10";
        Throwable t = Assertions.catchThrowable(() -> mockMvc.perform(get("/v1/admin/get/person/" + person_id)));
        org.junit.jupiter.api.Assertions.assertEquals(
                "Request processing failed; nested exception is com.example.demo.exceptions.PersonNotFoundException: Could not find person with ID=" + person_id,
                t.getMessage());
    }

    @Test
    void testThatPersonIsDeleted() throws Exception {
        String json = "{\"first_name\":\"haakdsadason\", \"last_name\":\"underdal\", \"date_of_birth\":\"1994-05-01\"}";

        // Create person and check that it was an success
        mockMvc.perform(post("/v1/admin/post/person").contentType(MediaType.APPLICATION_JSON).
                content(json)).
                andExpect(content().json(json));

        // Delete person
        mockMvc.perform(delete("/v1/admin/delete/person/" + ID));

        // Check that person is not present
        String person_id = "1";
        Throwable t = Assertions.catchThrowable(() -> mockMvc.perform(get("/v1/admin/get/person/" + ID++)));
        org.junit.jupiter.api.Assertions.assertEquals(
                "Request processing failed; nested exception is com.example.demo.exceptions.PersonNotFoundException: Could not find person with ID=" + person_id,
                t.getMessage());
    }

    @Test
    void testThatPersonIsUpdated() throws Exception {
        String json = "{\"first_name\":\"haakon\", \"last_name\":\"underdal\", \"date_of_birth\":\"1994-05-01\"}";
        String json_update = "{\"person_id\":" + ID + ", \"first_name\":\"OLA\", \"last_name\":\"underdal\", \"date_of_birth\":\"1994-05-01\"}";

        mockMvc.perform(post("/v1/admin/post/person").contentType(MediaType.APPLICATION_JSON).
                content(json)).
                andExpect(status().isOk());

        mockMvc.perform(put("/v1/admin/update/person/" + ID).contentType(MediaType.APPLICATION_JSON).
                content(json_update));

        mockMvc.perform(get("/v1/admin/get/person/" + ID++)).andExpect(content().json(json_update));
    }
}