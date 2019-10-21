package com.example.demo.controllers.adminControllers;

import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.PersonModel;
import com.example.demo.services.PersonService;
import org.assertj.core.api.Assertions;
import org.junit.Ignore;
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
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@AutoConfigureMockMvc
class AdministratorPersonControllerTest {

    @Autowired
    PersonService personService;

    @Autowired
    MockMvc mockMvc;

    public static int ID;

    @BeforeAll
    static void setUp() {
        ID = 1;
    }


    //@Test
    void testThatCanGetPersonAfterPost() throws Exception {
        String json = "{\"firstName\":\"haadasdaskon\", \"lastName\":\"underdal\", \"dateOfBirth\":\"1994-05-01\"}";

        mockMvc.perform(post("/v1/admin/post/person").contentType(MediaType.APPLICATION_JSON).
                content(json)).
                andExpect(status().isOk());

        mockMvc.perform(get("/v1/common/get/person/" + ID++)).andExpect(content().json(json));
    }

    /*
    * There is no easy way to capture nested exceptions
    */
    //@Test @Ignore
    void testThatExceptionIsThrownIfPersonDoesNotExist() throws ElementNotFoundException {
        String personId = "10";
        Throwable t = Assertions.catchThrowable(() -> mockMvc.perform(get("/v1/common/get/person/" + personId)));
        org.junit.jupiter.api.Assertions.assertEquals(
                "Request processing failed; nested exception is com.example.demo.exceptions.PersonNotFoundException: Could not find person with ID=" + personId,
                t.getMessage());
    }

    //@Test @Ignore
    void testThatPersonIsDeleted() throws Exception {
        String json = "{\"addressId\":1, \"firstName\":\"haakdsadason\", \"lastName\":\"underdal\", \"dateOfBirth\":\"1994-05-01\"}";



        // Create person and check that it was an success
        mockMvc.perform(post("/v1/admin/post/person")
                .contentType(MediaType.APPLICATION_JSON)
                .content(json))
                //.andExpect(content().json(json))
                .andDo(print());

        // Delete person

        //AddressModel address = addressService.createAddress(new AddressModel("BERGEN", "BERGEN", "BERGEN", "BERGEN"));
        //PersonModel person =  personService.save(new PersonModel("Kristian", "Solbakken", LocalDate.of(2015, 10, 10), address));
        PersonModel person = personService.findById(ID).get();
        System.out.println(person.getAddress());

        System.out.println(person.getDateOfBirth());



        mockMvc.perform(delete("/v1/admin/delete/person/" + ID));

        // Check that person is not present
        String personId = "1";
        Throwable t = Assertions.catchThrowable(() -> mockMvc.perform(get("/v1/common/get/person/" + ID++)));
        org.junit.jupiter.api.Assertions.assertEquals(
                "Request processing failed; nested exception is com.example.demo.exceptions.ElementNotFoundException: Could not find person with ID=" + personId,
                t.getMessage());
    }


    //@Test @Ignore
    void testThatPersonIsUpdated() throws Exception {
        String json = "{\"firstName\":\"haakon\", \"lastName\":\"underdal\", \"dateOfBirth\":\"1994-05-01\"}";
        String jsonUpdated = "{\"personId\":" + ID + ", \"firstName\":\"OLA\", \"lastName\":\"underdal\", \"dateOfBirth\":\"1994-05-01\"}";

        mockMvc.perform(post("/v1/admin/post/person").contentType(MediaType.APPLICATION_JSON).
                content(json)).
                andExpect(status().isOk());

        mockMvc.perform(put("/v1/admin/update/person/" + ID).contentType(MediaType.APPLICATION_JSON).
                content(jsonUpdated));

        mockMvc.perform(get("/v1/common/get/person/" + ID++)).andExpect(content().json(jsonUpdated));
    }
}