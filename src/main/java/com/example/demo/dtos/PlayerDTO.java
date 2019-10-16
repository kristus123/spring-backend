package com.example.demo.dtos;

import com.example.demo.models.PlayerModel;
import lombok.Getter;
import lombok.Setter;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Getter
@Setter
public class PlayerDTO {
    PlayerModel player;
    public List<PlayerTeamHistoryDTO> playerTeamHistory;

    public PlayerDTO() {
        playerTeamHistory = new ArrayList<>();
    }
}
