package com.example.demo.controllers.commonControllers;

import com.example.demo.models.ContactModel;
import com.example.demo.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/common")
public class CommonContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/get/contact/{id}")
    public ContactModel getContact(@PathVariable Integer id) {

        Optional<ContactModel> contact = contactService.findById(id);
        if(!contact.isPresent())
            return null;

        return contact.get();
    }

    @GetMapping("/get/contact")
    public List<ContactModel> getContacts() {

        List<ContactModel> contacts = contactService.findAll();

        if (contacts.isEmpty())
            return null;

        return contacts;
    }
}
