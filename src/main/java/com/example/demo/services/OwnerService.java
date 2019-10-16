package com.example.demo.services;

import com.example.demo.models.OwnerModel;
import com.example.demo.models.PersonModel;
import com.example.demo.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class OwnerService {

    @Autowired
    private OwnerRepository ownerRepository;

    public OwnerModel save(OwnerModel owner) {
        return ownerRepository.save(owner);
    }

    public OwnerModel update(OwnerModel owner, OwnerModel oldOwner) {
        OwnerModel updatedOwner = null;
        if(oldOwner.getOwnerId() == owner.getOwnerId()) {
            updatedOwner = save(owner);
        }
        return updatedOwner;
    }

    public OwnerModel create(PersonModel person) {
        return ownerRepository.save(new OwnerModel(person));
    }

    public void delete(OwnerModel owner) {
        ownerRepository.delete(owner);
    }
    public void delete(int id) {
        delete(ownerRepository.findById(id).get());
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
