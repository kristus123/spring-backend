package com.example.demo.models;

import com.example.demo.enums.UserRole;
import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
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
@NoArgsConstructor
@AllArgsConstructor
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

    @JsonIgnore
    @ManyToMany//(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "USER_PLAYER",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "player_id") }
    )
    private Set<PlayerModel> players = new HashSet<>();

    @JsonIgnore
    @ManyToMany//(cascade = { CascadeType.MERGE, CascadeType.PERSIST })
    @JoinTable(
            name = "USER_TEAM",
            joinColumns = { @JoinColumn(name = "id") },
            inverseJoinColumns = { @JoinColumn(name = "team_id") }
    )
    private Set<TeamModel> teams = new HashSet<>();


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

    public boolean addPlayer(PlayerModel player) {
        player.getUsers().add(this);
        return players.add(player);
    }

    public boolean deletePlayer(PlayerModel player) {
        player.getUsers().remove(this);
        return players.remove(player);
    }

    public boolean addTeam(TeamModel team) {
        team.getUsers().add(this);
        return teams.add(team);
    }

    public boolean deleteTeam(TeamModel team) {
        team.getUsers().remove(this);
        return teams.remove(team);
    }

}
