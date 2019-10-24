package com.example.demo.models;


import com.example.demo.enums.GoalType;
import com.example.demo.enums.GoalTypeConverter;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.Cascade;
import org.hibernate.envers.Audited;
import org.hibernate.envers.RelationTargetAuditMode;

import javax.persistence.*;

@Entity
@Table(name="MATCH_GOAL")
@Audited(targetAuditMode = RelationTargetAuditMode.NOT_AUDITED)
@Getter
@Setter
@NoArgsConstructor
public class MatchGoalModel {
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    @Column(name = "goal_id")
    private Integer goalId;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @ManyToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "player_id", referencedColumnName = "player_id")
    private PlayerModel player;

    @Convert(converter = GoalTypeConverter.class)
    @Column(name = "goal_type")
    private GoalType goalTypeEnum;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne
    @JoinColumn(name = "goal_type_id", referencedColumnName = "goal_type_id")
    private GoalTypeModel goalType;

    @Cascade(org.hibernate.annotations.CascadeType.SAVE_UPDATE)
    @OneToOne//(cascade = CascadeType.MERGE)
    @JoinColumn(name = "match_id", referencedColumnName = "match_id")
    private MatchModel match;

    private String description;

    public MatchGoalModel(PlayerModel player, GoalTypeModel goalType, MatchModel match, String description) {
        this.player = player;
        this.goalType = goalType;
        this.match = match;
        this.description = description;
    }
}
