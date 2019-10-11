package com.example.demo.services;

import com.example.demo.models.ContactModel;
import com.example.demo.repositories.ContactRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ContactService {

    @Autowired
    private ContactRepository contactRepository;

    public ContactModel save(ContactModel contactModel) {return contactRepository.save(contactModel);}
    public ContactModel update(Integer id, ContactModel contactModel) {
        if(!findById(id).isPresent())
            return null;
        contactModel.setContactId(id);
        return save(contactModel);
    }

    public void delete(Integer id) {contactRepository.deleteById(id);}
    public Optional<ContactModel> findById(Integer id) {return contactRepository.findById(id);}
    public List<ContactModel> findAll() {return contactRepository.findAll();}
}
