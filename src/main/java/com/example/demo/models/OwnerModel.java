package com.example.demo.models;

import com.example.demo.interfaces.LivingHuman;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="OWNER")
@Getter
@Setter
@NoArgsConstructor
public class OwnerModel implements LivingHuman {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "owner_id")
    private Integer ownerId;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private PersonModel person;

    public OwnerModel(PersonModel person) {
        this.person = person;
    }

    @Override
    public PersonModel getPersonObject() {
        return null;
    }
}
