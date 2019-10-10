package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="CONTACT")
@Getter
@Setter
public class ContactModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Integer contactId;

    @Column(nullable = false)
    private String contactType;

    @Column(nullable = false)
    private String contactDetail;

    @ManyToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private PersonModel person;

    public ContactModel() {
    }

    public ContactModel(String contactType, String contactDetail, PersonModel person) {
        this.contactType = contactType;
        this.contactDetail = contactDetail;
        this.person = person;
    }
}
