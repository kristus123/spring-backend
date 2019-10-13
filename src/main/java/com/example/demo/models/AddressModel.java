package com.example.demo.models;

import com.fasterxml.jackson.databind.annotation.JsonDeserialize;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="ADDRESS")
@Getter
@Setter
@NoArgsConstructor
public class AddressModel {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "address_id")
    private int addressId;

    @Column(nullable = false)
    private String[] addresses;

    @Column(nullable = false)
    private String postalCode;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;


    public AddressModel(String postalCode, String city, String country, String... addresses) {
        this.addresses = addresses;
        this.postalCode = postalCode;
        this.city = city;
        this.country = country;
    }
}
