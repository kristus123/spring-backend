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

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "address_id", referencedColumnName = "address_id")
    private int address_id;

    @Column
    private String name;

}
