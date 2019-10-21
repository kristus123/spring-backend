package com.example.demo.services;

import com.example.demo.dtos.ContactDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.ContactModel;
import com.example.demo.models.PersonModel;
import com.example.demo.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    ContactRepository contactRepository;

    @Autowired
    PersonService personService;

    private ContactModel convert(ContactDTO input) {
        Optional<PersonModel> person = personService.findById(input.getPersonId());

        if (!person.isPresent())
            return null;

        return new ContactModel(input.getContactType(), input.getContactDetail(), person.get());
    }

    public ContactModel save(ContactModel contactModel) {return contactRepository.save(contactModel);}

    public ContactModel create(ContactDTO input) throws ElementNotFoundException {

        ContactModel converted = convert(input);
        if (converted == null)
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        return save(converted);
    }

    public ContactModel update(Integer id, ContactDTO input) throws ElementNotFoundException {
        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find contact with ID=" + id));

        ContactModel updatedContact = convert(input);
        updatedContact.setContactId(id);
        return save(updatedContact);
    }



    public void delete(Integer id) {contactRepository.deleteById(id);}

    public ContactModel deleteById(Integer id) {
        ContactModel contact = findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find contact with ID=" + id));
        contactRepository.deleteById(id);
        return contact;
    }

    public Optional<ContactModel> findById(Integer id) {return contactRepository.findById(id);}
    public List<ContactModel> findAll() {return contactRepository.findAll();}
}
