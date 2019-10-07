package com.example.demo.models;


import lombok.Getter;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="GOAL_TYPE")
@Getter
@Setter
public class GoalTypeModel {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private Integer goal_type_id;

    @Column
    private String type;
}
