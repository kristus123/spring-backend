package com.example.demo.controllers;


import com.example.demo.models.PersonModel;
import com.example.demo.models.UserModel;
import com.example.demo.repositories.UserRepository;
import com.example.demo.services.AddressService;
import com.example.demo.services.PersonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/public")
public class PublicController {

    @Autowired
    AddressService addressService;

    @Autowired PersonService personService;

    @Autowired
    private UserRepository userRepository;

    @GetMapping("/allUsers")
    public List<UserModel> allUsers() {
        return userRepository.findAll();
    }


    @GetMapping("/getInfo")
    public List<PersonModel> getInfo() {

        System.out.println("_________");
        List<PersonModel> liste  = personService.findAllActive();
        liste.forEach(System.out::println);
        System.out.println("_________");

        return liste;
    }

    @GetMapping
    public String getMessage() {
        return "Hello from public API controller";
    }
}
