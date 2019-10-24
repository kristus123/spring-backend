package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.PlayerResourceAssembler;
import com.example.demo.dtos.PlayerDTO;
import com.example.demo.dtos.PlayerHistoryDTO;
import com.example.demo.dtos.PlayerTeamHistoryDTO;
import com.example.demo.models.PlayerHistoryModel;
import com.example.demo.models.PlayerModel;
import com.example.demo.repositories.audit.IPlayerHistoryRepository;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;

@RestController @RequestMapping("/v1/admin")
public class AdministratorPlayerController {
    @Autowired
    PlayerService playerService;

    @Autowired
    PlayerResourceAssembler assembler;

    private final IPlayerHistoryRepository playerHistoryRepository;

    @Autowired
    public AdministratorPlayerController(IPlayerHistoryRepository repo) {
        this.playerHistoryRepository = repo;
    }

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
