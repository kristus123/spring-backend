package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.sql.Date;

@Entity
@Table(name = "SEASON")
@Getter
@Setter
public class SeasonModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "season_id")
    private Integer seasonId;

    @Column(nullable = false)
    private Date startDate;

    @Column(nullable = false)
    private Date endDate;

    @Column(nullable = false)
    private String name;

    private String description;


    public SeasonModel() {
    }

    public SeasonModel(Date startDate, Date endDate, String name, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.description = description;
    }
}
