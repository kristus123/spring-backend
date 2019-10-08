package com.example.demo.models;

import com.example.demo.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="USER_MODEL")
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer user_id;

    private String username;

    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRole userRole;

    public UserModel() {}
    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    public UserModel(String username, String password, UserRole userRole) {
        this.username = username;
        this.password = password;
        this.userRole = userRole;
    }
}
