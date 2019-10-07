package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="COACH")
@Getter
@Setter
public class CoachModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer coach_id;

    @OneToMany(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private List<PersonModel> coachedPlayers = new ArrayList<PersonModel>();
}
