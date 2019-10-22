package com.example.demo.models;

import com.example.demo.services.PersonService;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.*;
import java.util.Optional;

@Entity @Table(name="CONTACT")
@Getter @Setter @NoArgsConstructor
public class ContactModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Integer contactId;

    @Column(nullable = false)
    private String contactType;

    @Column(nullable = false)
    private String contactDetail;

    @ManyToOne (cascade = CascadeType.MERGE)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private PersonModel person;

    public ContactModel(String contactType, String contactDetail, PersonModel person) {
        this.contactType = contactType;
        this.contactDetail = contactDetail;
        this.person = person;
    }
}
