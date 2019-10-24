package com.example.demo.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity
@Table(name="GOAL_TYPE_MODEL")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GoalTypeModel {

    @Id
    @GeneratedValue
    @Column(name = "goal_type_id")
    private Integer goalTypeId;

    @Column(name = "goal_type_name", unique = true)
    private String typeName;

    public GoalTypeModel(String typeName) {
        this.typeName = typeName;
    }

}
