package com.example.demo.dtos;

import com.example.demo.models.SeasonModel;
import com.example.demo.services.SeasonService;
import com.example.demo.services.TeamService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor
public class SeasonDTO {

    @Autowired
    SeasonService seasonService;

    private SeasonModel seasonModel;

    public SeasonDTO(SeasonModel seasonModel) {
        seasonModel.getSeasonId();
        this.seasonModel = seasonModel;
    }
}
