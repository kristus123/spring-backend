package com.example.demo.models;

import com.example.demo.interfaces.LivingHuman;
import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import jdk.nashorn.internal.ir.annotations.Ignore;
import lombok.*;
import org.hibernate.annotations.Cascade;
import static org.hibernate.annotations.CascadeType.SAVE_UPDATE;
import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name="PERSON")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PersonModel implements LivingHuman { //
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "person_id")
    private Integer personId;

    @Column(nullable = false)
    private String firstName;

    @Column(nullable = false)
    private String lastName;

    @Column(nullable = false)
    private LocalDate dateOfBirth;

    //@Cascade(SAVE_UPDATE)
    @OneToOne(cascade = {CascadeType.MERGE, CascadeType.REMOVE})
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressModel address;


    public PersonModel(String firstName, String lastName, LocalDate dateOfBirth, AddressModel address) {
        this(firstName, lastName, dateOfBirth);
        this.address = address;
    }

    public PersonModel(String firstName, String lastName, LocalDate dateOfBirth) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
    }


    @Override
    public String toString() {
        return "PERSONMODEL : " + firstName + " " + lastName;
    }

    @Override @JsonIgnore
    public PersonModel getPersonObject() {
        return this;
    }
}
