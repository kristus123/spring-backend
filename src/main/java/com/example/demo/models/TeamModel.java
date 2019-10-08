package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="TEAM")
@Getter
@Setter
public class TeamModel {
    @Id
    @GeneratedValue
    private Integer team_id; // TODO PANDA: use String instead??

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "association_id", referencedColumnName = "association_id")
    private AssociationModel association;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "coach_id", referencedColumnName = "coach_id")
    private CoachModel coach;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "owner_id")
    private OwnerModel owner;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private LocationModel location;

    public TeamModel() {
    }

    public TeamModel(AssociationModel association, CoachModel coach, OwnerModel owner, LocationModel location) {
        this.association = association;
        this.coach = coach;
        this.owner = owner;
        this.location = location;
    }
}
