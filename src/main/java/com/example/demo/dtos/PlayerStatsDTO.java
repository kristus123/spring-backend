package com.example.demo.dtos;

import com.example.demo.enums.GoalType;
import lombok.Getter;
import lombok.Setter;
import lombok.experimental.Accessors;

import java.util.HashMap;

@Getter
@Setter
@Accessors(chain = true)
public class PlayerStatsDTO {
    Integer totalGoals;
    Integer seasonGoals;
    Double averageGoal;
    HashMap<String, Integer> goalTypes;
}
