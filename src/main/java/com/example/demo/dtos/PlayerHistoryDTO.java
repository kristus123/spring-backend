package com.example.demo.dtos;

import com.example.demo.models.PlayerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PlayerHistoryDTO {
    PlayerModel player;
    public List<PlayerTeamHistoryDTO> playerTeamHistory;

    public PlayerHistoryDTO() {
        playerTeamHistory = new ArrayList<>();
    }
}
