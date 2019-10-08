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
    private Integer season_id;

    @Column(nullable = false)
    private Date start_date;

    @Column(nullable = false)
    private Date end_date;

    @Column(nullable = false)
    private String name;

    private String description;


    public SeasonModel() {
    }

    public SeasonModel(Date start_date, Date end_date, String name, String description) {
        this.start_date = start_date;
        this.end_date = end_date;
        this.name = name;
        this.description = description;
    }
}
