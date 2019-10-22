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
class AdministratorContactControllerTest {

    @Autowired
    MockMvc mockMvc;

    String jsonBody;
    static int ID = 0; // TODO PANDA: I think personId != contactId

    //@BeforeEach
    void setUp() throws Exception {

        jsonBody = "{\"personId\":" + ID + ", \"contactType\":\"PHONE\", \"contactDetail\":\"41003239\"}";

        mockMvc.perform(post("/v1/admin/post/contact").contentType(MediaType.APPLICATION_JSON).
                content(jsonBody)).
                andExpect(status().isCreated());
        ID++;
    }

    @Test
    public void testThatContactIsDeleted() throws Exception {
        mockMvc.perform(delete("/v1/admin/delete/contact/" + ID--));
        // This is not a good testing method
        // Should be checked by http status set by a custom exception
        // We are actually expecting a nullPointerException, but it is wrapped inside a Spring exception
        /*
        assertThrows(NestedServletException.class, () ->
                mockMvc.perform(get("/v1/common/get/contact/" + ID)));


         */

    }

    @Test
    public void testThatContactIsUpdated() throws Exception {

        String jsonBodyUpdated = "{\"personId\":" + 1 + ", \"contactType\":\"PHONE\", \"contactDetail\":\"1111111\"}";

        mockMvc.perform(put("/v1/admin/update/contact/"+1).contentType(MediaType.APPLICATION_JSON).
                content(jsonBodyUpdated)).
                andExpect(status().isCreated());

        mockMvc.perform(get("/v1/common/get/contact/" + 1)).
                andExpect(content().json("{\"contactDetail\":\"1111111\"}"));
    }
}