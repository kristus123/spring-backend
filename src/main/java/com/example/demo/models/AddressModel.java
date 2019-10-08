package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="ADDRESS")
@Getter
@Setter
public class AddressModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer address_id;

    @Column(nullable = false)
    private String[] addresses;

    @Column(nullable = false)
    private int postal_code;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    public AddressModel() {}
    public AddressModel(int postalCode, String city, String country, String... addresses) {
        this.addresses = addresses;
        this.postal_code = postal_code;
        this.city = city;
        this.country = country;
    }
}
