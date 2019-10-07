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
    @Column
    private String address_line_1;

    @Column
    private int postal_code;

    @Column
    private String city;

    @Column
    private String country;

}
