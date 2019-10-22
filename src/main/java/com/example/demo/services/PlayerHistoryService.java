package com.example.demo.services;

import com.example.demo.dtos.PlayerHistoryDTO;
import com.example.demo.dtos.PlayerTeamHistoryDTO;
import com.example.demo.models.PlayerHistoryModel;
import com.example.demo.models.PlayerModel;
import com.example.demo.repositories.audit.PlayerHistoryRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class PlayerHistoryService {

    @Autowired
    private PlayerHistoryRepository playerHistoryRepository;

    public PlayerHistoryDTO getPlayerAndPreviousTeams(PlayerModel playerModel) {
        PlayerHistoryDTO playerHistoryDTO = new PlayerHistoryDTO();
        playerHistoryDTO.setPlayer(playerModel);
        List<PlayerHistoryModel> list = playerHistoryRepository.listPlayerHistoryRevisions(playerModel.getPlayerId());
        playerHistoryDTO.setPlayerTeamHistory(createPreviousTeamList(list));
        return playerHistoryDTO;
    }

    private List<PlayerTeamHistoryDTO> createPreviousTeamList(List<PlayerHistoryModel> playerHistoryModelList) {
        Integer tempTeamId = null;
        List<PlayerTeamHistoryDTO> previousTeams = new ArrayList<>();
        for(PlayerHistoryModel player_hist : playerHistoryModelList) {
            // Player has changed team
            if(player_hist.getPlayerModel().getTeam().getTeamId() != tempTeamId) {
                previousTeams.add(new PlayerTeamHistoryDTO(
                        player_hist.getPlayerModel().getTeamDateFrom(),
                        player_hist.getPlayerModel().getTeamDateTo(),
                        player_hist.getPlayerModel().getTeam().getTeamId()

                ));
                tempTeamId = player_hist.getPlayerModel().getTeam().getTeamId();
            }
        }
        return previousTeams;
    }


}
