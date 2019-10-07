package com.example.demo.models;

import com.example.demo.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;

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

    @Id @GeneratedValue private int id;

    @Column
    private String username;

    @Transient
    @Column(length = 80)
    @Size(min = 8 , max = 60)
    private String password;

    @Enumerated(EnumType.STRING)
    @Column
    private UserRole userRole;

}
