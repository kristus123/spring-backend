package com.example.demo.services;

import com.example.demo.models.OwnerModel;
import com.example.demo.repositories.OwnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

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

    public void delete(OwnerModel owner) {
        ownerRepository.delete(owner);
    }

    public Optional<OwnerModel> findById(int id) {
        return ownerRepository.findById(id);
    }
}
