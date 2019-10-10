package com.example.demo.controllers.adminControllers;

import com.example.demo.models.ContactModel;
import com.example.demo.services.ContactService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorContactController {

    @Autowired
    ContactService contactService;

    @GetMapping("/get/contact/{id}")
    public ContactModel getContact(@PathVariable Integer id) {
        ContactModel contactModel = contactService.findById(id).orElseGet(null);
        return contactModel;
    }

    @GetMapping("/get/contact")
    public List<ContactModel> getAllContact() {
        return contactService.findAll();
    }

    @PostMapping("/post/contact")
    public ContactModel createContact(@RequestBody ContactModel contact) {
        return contactService.save(contact);
    }

    @PutMapping("/update/contact/{id}")
    public ContactModel updateContact(@PathVariable Integer id, @RequestBody ContactModel contact) {
        return contactService.update(id, contact);
    }

    @DeleteMapping("/delete/contact/{id}")
    public void deleteContact(@PathVariable Integer id) {
        contactService.delete(id);
    }

}