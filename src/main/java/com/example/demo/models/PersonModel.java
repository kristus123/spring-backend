package com.example.demo.models;

import com.example.demo.interfaces.LivingHuman;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;
import java.time.LocalDateTime;

import javax.persistence.*;

@Entity
@Table(name="PERSON")
@Getter
@Setter
@NoArgsConstructor
public class PersonModel implements LivingHuman {
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

    @OneToOne(cascade = CascadeType.MERGE)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressModel address;


    public PersonModel(String firstName, String lastName, LocalDate dateOfBirth, AddressModel address) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.dateOfBirth = dateOfBirth;
        this.address = address;
    }

    @Override
    public String toString() {
        return "PERSONMODEL : " + firstName + " " + lastName;
    }

    @Override
    public PersonModel getPerson() {
        return this;
    }
}
