package com.example.demo.models;

import com.example.demo.interfaces.LivingHuman;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="COACH")
@Getter
@Setter
@NoArgsConstructor
public class CoachModel implements LivingHuman {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "coach_id")
    private Integer coachId;

    @OneToOne(cascade = CascadeType.MERGE,  orphanRemoval = true)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private PersonModel person;



    public CoachModel(PersonModel person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "coachname is " + person.getFirstName();
    }
}
