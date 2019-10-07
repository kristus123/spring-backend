package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="TEAM")
@Getter
@Setter
public class TeamModel {
    @Id
    @GeneratedValue
    private int team_id;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "coach_id", referencedColumnName = "coach_id")
    private int coach_id;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "owner_id", referencedColumnName = "owner_id")
    private int owner_id;

    @OneToOne (cascade = CascadeType.ALL)
    @JoinColumn(name = "location_id", referencedColumnName = "location_id")
    private int location_id;

}
