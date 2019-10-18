package com.example.demo.dtos;

import com.example.demo.models.TeamModel;
import com.example.demo.services.TeamService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor
public class TeamDTO {
    private int ownerId;
    private int locationId;
    private int associationId;
    private int coachId;
}
