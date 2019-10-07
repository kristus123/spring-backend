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
    private Integer association_id;

    @Size(min = 4, max = 255, message = "Minimum Association name length: 4 characters")
    @Column(unique = true, nullable = false)
    private String association_name;
    @Size(min = 21, max = 255, message = "Minimum Association name length: 21 characters")
    @Column(unique = true, nullable = false)
    private String description;
}
