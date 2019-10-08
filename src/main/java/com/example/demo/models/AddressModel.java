package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="ADDRESS")
@Getter
@Setter
public class AddressModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer address_id;

    @Column(nullable = false)
    private String address_line_1;

    private String address_line_2;

    private String address_line_3;

    @Column(nullable = false)
    private int postal_code;

    @Column(nullable = false)
    private String city;

    @Column(nullable = false)
    private String country;

    public AddressModel() {}
    public AddressModel(String address_line_1, String address_line_2, String address_line_3, int postal_code, String city, String country) {
        this.address_line_1 = address_line_1;
        this.address_line_2 = address_line_2;
        this.address_line_3 = address_line_3;
        this.postal_code = postal_code;
        this.city = city;
        this.country = country;
    }
}
