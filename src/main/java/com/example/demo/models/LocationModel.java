package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="LOCATION")
@Getter
@Setter
public class LocationModel {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer location_id;

    @OneToOne(cascade = CascadeType.MERGE) //endret fra CascadeType.ALL til MERGE
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressModel address;

    @Column(nullable = false)
    private String name;

    private String description;

    public LocationModel() {
    }

    public LocationModel(AddressModel address, String name, String description) {
        this.address = address;
        this.name = name;
        this.description = description;
    }
}
