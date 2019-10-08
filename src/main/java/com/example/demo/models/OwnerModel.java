package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="OWNER")
@Getter
@Setter
public class OwnerModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer owner_id;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private PersonModel person;

    /*
    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private List<PersonModel> ownedPlayers = new ArrayList<PersonModel>();
     */

    public OwnerModel() {
    }

    public OwnerModel(PersonModel person) {
        this.person = person;
    }
}
