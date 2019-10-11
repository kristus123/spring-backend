package com.example.demo.services;

import com.example.demo.exceptions.PersonNotFoundException;
import com.example.demo.models.PersonModel;
import com.example.demo.repositories.PersonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    public PersonModel save(PersonModel personModel) { return personRepository.save(personModel);}
    public PersonModel update(Integer id, PersonModel personModel) {
        if(!findById(id).isPresent())
            return null;
        // This must be set if the id is not set in the json
        personModel.setPersonId(id);
        return save(personModel);

    }
    public void delete(Integer id) {personRepository.deleteById(id);}
    public Optional<PersonModel> findById(Integer id) {return personRepository.findById(id);}
    public List<PersonModel> findAll() {return personRepository.findAll();}
}
