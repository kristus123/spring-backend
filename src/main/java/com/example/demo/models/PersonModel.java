package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import java.sql.Date;
import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="PERSON")
@Getter
@Setter
public class PersonModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer person_id;

    @Column(nullable = false)
    private String first_name;

    @Column(nullable = false)
    private String last_name;

    @Column(nullable = false)
    private Date date_of_birth;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressModel address;

    public PersonModel() {
    }

    public PersonModel(String first_name, String last_name, Date date_of_birth, AddressModel address) {
        this.first_name = first_name;
        this.last_name = last_name;
        this.date_of_birth = date_of_birth;
        this.address = address;
    }
}
