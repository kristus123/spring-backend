package com.example.demo.controllers.adminControllers;

import com.example.demo.models.ContactModel;
import com.example.demo.models.PersonModel;
import com.example.demo.services.ContactService;
import com.example.demo.services.PersonService;
import org.omg.SendingContext.RunTime;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorContactController {

    @Autowired
    ContactService contactService;

    @Autowired
    PersonService personService;

    @GetMapping("/get/contact/{id}")
    public ContactModel getContact(@PathVariable Integer id) {
        ContactModel contactModel = contactService.findById(id).orElseGet(null);
        return contactModel;
    }

    @GetMapping("/get/contact")
    public List<ContactModel> getAllContacts() {
        return contactService.findAll();
    }

    @PostMapping("/post/contact")
    public ContactModel createContact(@RequestBody Map<String, String> response) {
        Optional<PersonModel> person = personService.findById(Integer.parseInt(response.get("personId")));
        if (person.isPresent()) {
            ContactModel contactModel = new ContactModel();
            contactModel.setContactType(response.get("contactType"));
            contactModel.setContactDetail(response.get("contactDetail"));

            contactModel.setPerson(person.get());
            return contactService.save(contactModel);
        }
        throw new RuntimeException("person ID not found");

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