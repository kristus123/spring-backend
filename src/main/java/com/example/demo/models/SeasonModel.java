package com.example.demo.models;

import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;

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
    private LocalDate startDate;

    @Column(nullable = false)
    private LocalDate endDate;

    @Column(nullable = false)
    private String name;

    private String description;


    public SeasonModel() {
    }

    public SeasonModel(LocalDate startDate, LocalDate endDate, String name, String description) {
        this.startDate = startDate;
        this.endDate = endDate;
        this.name = name;
        this.description = description;
    }
}
