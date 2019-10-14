package com.example.demo.models;

import com.example.demo.enums.UserRole;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.validator.constraints.Length;

import javax.persistence.*;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

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

    @OneToMany(cascade = CascadeType.MERGE,  orphanRemoval = true)
    @JoinColumn(name = "id", referencedColumnName = "player_id")
    private List<PlayerModel> favPlayers;

    @OneToMany(cascade = CascadeType.MERGE,  orphanRemoval = true)
    @JoinColumn(name = "id", referencedColumnName = "team_id")
    private List<TeamModel> favTeams;


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
