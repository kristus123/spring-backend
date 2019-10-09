package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name="ASSOCIATION")
@Getter
@Setter
public class AssociationModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "association_id")
    private Integer associationId;

    @Size(min = 4, max = 255, message = "Minimum Association name length: 4 characters")
    @Column(unique = true, nullable = false)
    private String name;

    @Size(min = 21, max = 255, message = "Minimum Association name length: 21 characters")
    @Column(unique = true, nullable = false)
    private String description;

    public AssociationModel() {
    }

    // TODO PANDA: for testing purposes
    public AssociationModel(Integer id, String name) {
        this.associationId = id;
        this.name = name;
        this.description = "very cool and very long description to fill up minimum requirement..";
    }

    public AssociationModel(@Size(min = 4, max = 255, message = "Minimum Association name length: 4 characters") String name, @Size(min = 21, max = 255, message = "Minimum Association name length: 21 characters") String description) {
        this.name = name;
        this.description = description;
    }
}
