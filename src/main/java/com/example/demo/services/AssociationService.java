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

    public AssociationModel update(AssociationModel associationModel, AssociationModel oldAssociationModel) {
        AssociationModel updatedAssociation = null;
        if(oldAssociationModel.getAssociationId() == associationModel.getAssociationId()) {
            updatedAssociation = save(associationModel);
        }

        return updatedAssociation;
    }

    public void delete(AssociationModel association) {
        associationRepository.delete(association);
    }

    public void deleteById(int id) {
        associationRepository.deleteById(id);
    }

    public Optional<AssociationModel> findById(int id) {
        return associationRepository.findById(id);
    }

    public List<AssociationModel> findAll() {
        return associationRepository.findAll();
    }
}
