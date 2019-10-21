package com.example.demo.services;

import com.example.demo.dtos.OwnerDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.OwnerModel;
import com.example.demo.models.PersonModel;
import com.example.demo.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.security.acl.Owner;
import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    OwnerRepository ownerRepository;

    @Autowired
    PersonService personService;

    private OwnerModel convert(OwnerDTO input) {
        Optional<PersonModel> person = personService.findById(input.getPersonId());

        if (!person.isPresent())
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        return new OwnerModel(person.get());
    }

    public OwnerModel save(OwnerModel owner) {
        return ownerRepository.save(owner);
    }

    public OwnerModel create(OwnerDTO input) throws ElementNotFoundException {
        OwnerModel converted = convert(input);
        return save(converted);
    }

    public OwnerModel update(Integer id, OwnerDTO input) throws ElementNotFoundException {

        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find team with ID=" + id));

        OwnerModel updatedOwner = convert(input);
        updatedOwner.setOwnerId(id);
        return save(updatedOwner);
    }

    public OwnerModel create(PersonModel person) {
        return ownerRepository.save(new OwnerModel(person));
    }

    public void delete(OwnerModel owner) {
        ownerRepository.delete(owner);
    }

    public OwnerModel deleteById(int id) throws ElementNotFoundException {
        OwnerModel owner = findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find owner with ID=" + id));
        delete(ownerRepository.findById(id).get());
        return owner;
    }

    public Optional<OwnerModel> findById(int id) {
        return ownerRepository.findById(id);
    }


    public Optional<OwnerModel> findByPerson(PersonModel personModel) {
        return ownerRepository.findByPerson(personModel);
    }

    public List<OwnerModel> findAll() {
        return ownerRepository.findAll();
    }

}
