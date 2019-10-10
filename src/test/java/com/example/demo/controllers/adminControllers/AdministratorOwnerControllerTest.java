package com.example.demo.controllers.adminControllers;

import com.example.demo.repositories.OwnerRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
public class AdministratorOwnerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Autowired
    OwnerRepository ownerRepository;

    @Test
    void runTests() throws Exception {
        addOwner();
        getOwner();
        updateOwner();
        deleteOwner();
    }

    void getOwner() throws Exception {
        mockMvc.perform(get("/v1/admin/get/owner/1"))
                .andDo(print())
                .andExpect(content().json("{\"ownerId\" : 1, \"person\" : null}"))
                .andExpect(status().isOk());
    }

    void addOwner() throws Exception {
        String json = "{\"person_id\" : 2}";
        mockMvc.perform(post("/v1/admin/post/owner")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    //This endpoint is difficult to test because of the table structure.
    void updateOwner() throws Exception {
        String json = "{\"ownerId\" : 1, \"person\" : null}";
        mockMvc.perform(put("/v1/admin/update/owner/1")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                .andDo(print())
                .andExpect(status().isOk());
    }

    void deleteOwner() throws Exception {
        mockMvc.perform(delete("/v1/admin/delete/owner/1"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}
