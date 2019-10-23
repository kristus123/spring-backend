package com.example.demo.controllers.commonControllers;

import com.example.demo.services.PersonService;
import org.junit.jupiter.api.BeforeAll;
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
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class CommonPersonControllerTest {


    @Autowired
    PersonService personService;

    @Autowired
    MockMvc mockMvc;

    public static int ID;

    @BeforeAll
    static void setUp() {
        ID = 1;
    }

    @Test
    void getPerson() {
    }

    @Test
    void getPeople() {
    }

    //@Test
    void testThatCanGetAllPeople() throws Exception {
        String json = "{\"addressId\": 1, \"firstName\":\"haakon\", \"lastName\":\"underdal\", \"dateOfBirth\":\"1994-05-01\"}";
        mockMvc.perform(post("/v1/admin/post/person").contentType(MediaType.APPLICATION_JSON).
                content(json)).
                andExpect(status().isCreated());
        ID++;
        mockMvc.perform(get("/v1/common/get/person/")).andExpect(jsonPath("$._embedded.personModelList", hasSize(4)));
    }
}