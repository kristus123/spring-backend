package com.example.demo.models;

import com.example.demo.interfaces.LivingHuman;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

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

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne//(cascade = CascadeType.MERGE,  orphanRemoval = true)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private PersonModel person;

    @JsonIgnore
    @Column(name = "active")
    private boolean active = true;

    public CoachModel(PersonModel person) {
        this.person = person;
    }

    @Override
    public String toString() {
        return "coachname is " + person.getFirstName();
    }


    @Ignore
    @Override
    public PersonModel getPersonObject() {
        return null;
    }
}
