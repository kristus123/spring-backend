package com.example.demo.services;

import com.example.demo.models.AssociationModel;
import com.example.demo.repositories.AssociationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AssociationService {

    @Autowired
    private AssociationRepository associationRepository;

    /** TODO PANDA: remove comment
        calling save() on an object with predefined id will update the corresponding database record
        rather than insert a new one, and also explains why save() is not called create()
     */
    public AssociationModel save(AssociationModel association) {
        return associationRepository.save(association);
    }

    public void deleteById(Integer id) {
        associationRepository.deleteById(id);
    }

    public Optional<AssociationModel> findById(Integer id) {
        return associationRepository.findById(id);
    }

    public List<AssociationModel> findAll() {
        return associationRepository.findAll();
    }

    public Optional<AssociationModel> findByName(String name) {return associationRepository.findByName(name);}
}
