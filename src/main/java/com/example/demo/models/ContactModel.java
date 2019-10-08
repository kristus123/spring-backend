package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="CONTACT")
@Getter
@Setter
public class ContactModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer contact_id;

    @Column(nullable = false)
    private String contact_type;

    @Column(nullable = false)
    private String contact_detail;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private PersonModel person;

    public ContactModel() {
    }

    public ContactModel(String contact_type, String contact_detail, PersonModel person) {
        this.contact_type = contact_type;
        this.contact_detail = contact_detail;
        this.person = person;
    }
}
