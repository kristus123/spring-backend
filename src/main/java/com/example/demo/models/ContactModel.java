package com.example.demo.models;

import com.example.demo.enums.ContactType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;

import javax.persistence.*;

@Entity
@Table(name="CONTACT")
@Getter
@Setter
@NoArgsConstructor
public class ContactModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "contact_id")
    private Integer contactId;

    @Column(nullable = false)
    @Enumerated(EnumType.STRING)
    private ContactType contactType;

    @Column(nullable = false)
    private String contactDetail;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne// (cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id", referencedColumnName = "person_id")
    private PersonModel person;

    public ContactModel(ContactType contactType, String contactDetail, PersonModel person) {
        this.contactType = contactType;
        this.contactDetail = contactDetail;
        this.person = person;
    }
}
