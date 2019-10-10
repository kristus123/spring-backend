package com.example.demo.controllers.adminControllers;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.util.NestedServletException;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdministratorCoachControllerTest {

    @Autowired
    MockMvc mockMvc;

    String jsonBody;
    static int ID = 0;

    @BeforeEach
    void setUp() throws Exception {
        jsonBody = "{\"personId\": 1 }";

        mockMvc.perform(post("/v1/admin/post/coach").contentType(MediaType.APPLICATION_JSON).
                content(jsonBody)).
                andExpect(status().isOk());
        ID++;
    }

    @Test
    public void testThatCanGetCoachAfterPost() throws Exception {
        mockMvc.perform(get("/v1/admin/get/coach/" + ID)).andExpect(content().json("{\"coachId\": " + ID + "}"));
    }

    @Test
    public void testThatCanGetAllCoaches() throws Exception {
        mockMvc.perform(get("/v1/admin/get/coach/")).andExpect(jsonPath("$", hasSize(1)));
    }

    @Test
    public void testThatCoachIsDeleted() throws Exception {
        mockMvc.perform(delete("/v1/admin/delete/coach/" + ID));
        // This is not a good testing method
        // Should be checked by http status set by a custom exception
        // We are actually expecting a nullPointerException, but it is wrapped inside a Spring exception
        assertThrows(NestedServletException.class, () ->
                mockMvc.perform(get("/v1/admin/get/coach/" + ID)));
    }

    @Test
    public void testThatCoachIsUpdated() throws Exception {
        // TODO: Add person object to coach
//        String person_json = "{\"personId\":\"2\", \"addressId\":\"1\", \"first_name\":\"haakon\", \"last_name\":\"underdal\", \"date_of_birth\":\"1994-05-01\"}";
//        String jsonBodyUpdated = "{\"coachId\":" + 2 + ",  \"personId\": 1 }";
//
//        mockMvc.perform(post("/v1/admin/post/person").contentType(MediaType.APPLICATION_JSON).
//                content(person_json)).
//                andExpect(status().isOk());
//
//        jsonBody = "{\"personId\": 2 }";
//
//        mockMvc.perform(post("/v1/admin/post/coach").contentType(MediaType.APPLICATION_JSON).
//                content(jsonBody)).
//                andExpect(status().isOk());
//
//        mockMvc.perform(put("/v1/admin/update/coach/"+ID).contentType(MediaType.APPLICATION_JSON).
//                content(jsonBodyUpdated)).
//                andExpect(status().isOk());
//
//        mockMvc.perform(get("/v1/admin/get/coach/" + ID)).
//                andExpect(content().json("{\"personId\":" + person_json + "}"));
    }

}