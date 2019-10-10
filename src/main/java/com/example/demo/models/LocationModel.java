package com.example.demo.models;


import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="LOCATION")
@Getter
@Setter
@NoArgsConstructor
public class LocationModel {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "location_id")
    private int locationId;

    @OneToOne(cascade = CascadeType.ALL) //endret fra CascadeType.ALL til MERGE
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private AddressModel address;

    @Column(nullable = false)
    private String name;

    private String description;


    public LocationModel(AddressModel address, String name, String description) {
        this.address = address;
        this.name = name;
        this.description = description;
    }
}
