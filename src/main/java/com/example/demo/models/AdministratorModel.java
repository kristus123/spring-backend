package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

import javax.persistence.*;
import javax.validation.constraints.Size;

@Entity
@Table(name = "ADMINISTRATOR")
@Getter
@Setter
public class AdministratorModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer admin_id;

    @Size(min = 8, message = "Minimum password length: 8 characters")
    private String password;

    private String username;

    public AdministratorModel() {}
    public AdministratorModel(@Size(min = 8, message = "Minimum password length: 8 characters") String password, String username) {
        this.password = password;
        this.username = username;
    }
}
