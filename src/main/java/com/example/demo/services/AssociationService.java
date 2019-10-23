package com.example.demo.services;

import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.AssociationModel;
import com.example.demo.repositories.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssociationService {

    @Autowired
    AssociationRepository associationRepository;

    public AssociationModel save(AssociationModel association) {
        return associationRepository.save(association);
    }

    public AssociationModel update(Integer id, AssociationModel association) throws ElementNotFoundException {
        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find association with ID=" + id));
        association.setAssociationId(id);
        return save(association);
    }

    public void delete(AssociationModel association) {
        associationRepository.delete(association);
    }

    public AssociationModel deleteById(int id) {
        AssociationModel association = findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find association with ID=" + id));
        associationRepository.deleteById(id);
        return association;
    }

    public Optional<AssociationModel> findById(int id) {
        return associationRepository.findById(id);
    }

    public List<AssociationModel> findAll() {
        return associationRepository.findAll();
    }

    public Optional<AssociationModel> findByName(String name) {return associationRepository.findByName(name);}


    public AssociationModel create(AssociationModel associationModel) {
        return associationRepository.save(associationModel);
    }
}
