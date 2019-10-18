package com.example.demo.dtos;

import com.example.demo.models.PlayerModel;
import com.example.demo.services.PlayerService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PlayerAnonDTO {

    @Autowired
    PlayerService playerService;

    private PlayerModel playerModel;
    private String playerName;
    private String teamName;

    public PlayerAnonDTO(PlayerModel playerModel, String playerName, String teamName) {
        this.playerModel = playerModel;
        this.playerName = playerName;
        this.teamName = teamName;

    }

}
