package com.example.demo.models;

import com.example.demo.enums.UserRole;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="users")
@Getter
@Setter
public class UserModel {

    public UserModel() {}
    public UserModel(String username, String password) {
        this.username = username;
        this.password = password;
    }

    @Id @GeneratedValue private Integer id;

    @Column
    private String username;

    @Column
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRole userRole;

    @Transient
    private String passwordConfirm;

}
