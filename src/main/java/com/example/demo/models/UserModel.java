package com.example.demo.models;

import com.example.demo.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@Table(name="USER_MODEL")
@Getter
@Setter
public class UserModel {

    @Id
    @GeneratedValue
    private int id;

    private String username;

    @Column(length = 80)
    @Size(min = 8 , max = 60)
    private String password;

    @Column
    private String[] roles;


    // Watchlist properties

    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "USER_PLAYER",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "player_id") }
    )
    Set<PlayerModel> players = new HashSet<>();


    @ManyToMany(cascade = { CascadeType.MERGE })
    @JoinTable(
            name = "USER_TEAM",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "team_id") }
    )
    Set<TeamModel> teams = new HashSet<>();


    public UserModel() {}
    public UserModel(String username, String password, UserRole... roles) {
        this.username = username;
        this.password = password;

        String[] liste = new String[roles.length];

        for (int i = 0 ; i < roles.length ; i++) {
            liste[i] = roles[i].getRole();
        }

        this.roles = liste;

        System.out.println(this.roles);
    }

    public void changeRole(UserRole role) {
        this.roles = new String[] {role.getRole()};
    }
}
