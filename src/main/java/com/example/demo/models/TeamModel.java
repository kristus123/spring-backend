package com.example.demo.models;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;

@Entity
@Table(name="TEAM")
@Getter
@Setter
@NoArgsConstructor
public class TeamModel {
    @Id
    @GeneratedValue
    @Column(name = "team_id")
    private Integer teamId;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "association_id", referencedColumnName = "association_id")
    private AssociationModel association;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "coach_id", referencedColumnName = "coach_id")
    private CoachModel coach;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "owner_id")
    private OwnerModel owner;

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private LocationModel location;

    // Watchlist properties

    @ManyToMany(mappedBy = "teams")
    private Set<UserModel> users = new HashSet<>();


    // TODO PANDA: for testing purposes
    public TeamModel(Integer teamId, Integer association_id, String associationName) {
        this.teamId = teamId;
        this.association = new AssociationModel(association_id, associationName);
    }

    public TeamModel(AssociationModel association) {
        this.association = association;
    }

    public TeamModel(AssociationModel association, CoachModel coach, OwnerModel owner, LocationModel location) {
        this.association = association;
        this.coach = coach;
        this.owner = owner;
        this.location = location;
    }

    @Override
    public String toString() {
        return association.getName();
        //return "TEAMMODEL : Association : " + association.getName() + " Coach : " + coach.getPerson().getFirstName() + " owner : " + owner.getPerson().getFirstName() + " addresse :" +location.getAddress().getAddresses()[0] ;
    }
}
