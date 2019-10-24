package com.example.demo.repositories;

import com.example.demo.models.AssociationModel;
import com.example.demo.models.CoachModel;
import com.example.demo.models.OwnerModel;
import com.example.demo.models.TeamModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface TeamRepository extends JpaRepository<TeamModel, Integer> {
    Optional<TeamModel> findByAssociation(AssociationModel associationModel);

     List<TeamModel> findByCoach(CoachModel coach);

    List<TeamModel> findByOwner(OwnerModel coach);
}
