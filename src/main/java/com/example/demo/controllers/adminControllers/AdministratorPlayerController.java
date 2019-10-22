package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.PlayerResourceAssembler;
import com.example.demo.dtos.PlayerDTO;
import com.example.demo.models.PlayerModel;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

@RestController @RequestMapping("/v1/admin")
public class AdministratorPlayerController {
    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerResourceAssembler assembler;

    /*

    private final IPlayerHistoryRepository playerHistoryRepository;

    @Autowired
    public AdministratorPlayerController(IPlayerHistoryRepository repo) {
        this.playerHistoryRepository = repo;
    }

    @GetMapping("/get/player/{playerId}/history")
    public PlayerHistoryDTO getPlayerHistory(@PathVariable int playerId) {
        if(!playerService.findById(playerId).isPresent())
            return null;
        PlayerHistoryDTO playerHistoryDTO = new PlayerHistoryDTO();
        playerHistoryDTO.setPlayer(playerService.findById(playerId).get());
        List<PlayerHistoryModel> list = playerHistoryRepository.listPlayerHistoryRevisions(playerId);
        Integer tempTeamId = -1;
        for(PlayerHistoryModel player_hist : list) {
            if(player_hist.getPlayerModel().getTeam().getTeamId() != tempTeamId) {
                playerHistoryDTO.getPlayerTeamHistory().add(new PlayerTeamHistoryDTO(
                        player_hist.getPlayerModel().getTeamDateFrom(),
                        player_hist.getPlayerModel().getTeamDateTo(),
                        player_hist.getPlayerModel().getTeam().getTeamId()
                ));
                tempTeamId = player_hist.getPlayerModel().getTeam().getTeamId();
            }
        }
        return playerHistoryDTO;
    }

     */

    @PostMapping("/post/player")
    public ResponseEntity<Resource<PlayerModel>> addPlayer(@RequestBody PlayerDTO playerModel) throws URISyntaxException {
        PlayerModel newPlayer = playerService.create(playerModel);
        Resource<PlayerModel> resource = assembler.toResource(newPlayer);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @PutMapping("/update/player/{playerId}")
    public ResponseEntity<Resource> updatePlayer(@PathVariable int playerId, @RequestBody PlayerDTO playerModel) throws URISyntaxException {
        PlayerModel updated = playerService.update(playerId, playerModel);
        Resource resource = assembler.toResource(updated);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    @DeleteMapping("/delete/player/{playerId}")
    public ResponseEntity<PlayerModel> deletePlayer(@PathVariable int playerId) {
        PlayerModel player = playerService.deleteById(playerId);
        return ResponseEntity.ok(player);
    }

}
