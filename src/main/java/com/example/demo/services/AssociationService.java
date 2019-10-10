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

    public AssociationModel save(AssociationModel association) {
        return associationRepository.save(association);
    }

    public void deleteById(int id) {
        associationRepository.deleteById(id);
    }

    public AssociationModel update(AssociationModel associationModel, AssociationModel oldAssociationModel) {
        AssociationModel updatedAssociation = null;
        if(oldAssociationModel.getAssociation_id() == associationModel.getAssociation_id()) {
            updatedAssociation = save(associationModel);
        }

        return updatedAssociation;
    }

    public Optional<AssociationModel> findById(int id) {
        return associationRepository.findById(id);
    }

    public List<AssociationModel> findAll() {
        return associationRepository.findAll();
    }
}
