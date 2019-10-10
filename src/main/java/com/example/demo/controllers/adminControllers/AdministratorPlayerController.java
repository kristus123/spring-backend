package com.example.demo.controllers.adminControllers;

import com.example.demo.assembler.PlayerResourceAssembler;
import com.example.demo.models.PlayerModel;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.*;

import java.net.URISyntaxException;
import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorPlayerController {

    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerResourceAssembler playerResourceAssembler;



    @PostMapping("/post/player")
    public Resource<PlayerModel> newPlayer(@RequestBody PlayerModel playerModel) throws URISyntaxException {

        Resource<PlayerModel> resource = playerResourceAssembler.toResource(playerService.save(playerModel));
        /*
        ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
         */

        return resource;
    }

    @PutMapping("/update/player/{id}")
    public Resource<PlayerModel> updatePlayer(@PathVariable Integer id, @RequestBody PlayerModel playerModel) {
        if (playerModel.getPlayerId() != id || !playerService.findById(id).isPresent()) {
            //ResponseEntity.badRequest().build();
            return null;
        }

        //return ResponseEntity.ok(associationService.save(associationModel));
        return playerResourceAssembler.toResource(playerService.save(playerModel));
    }

    @DeleteMapping("/delete/player/{id}")
    public Resource<PlayerModel> deletePlayer(@PathVariable Integer id) {
        Optional<PlayerModel> player = playerService.findById(id);
        if (!player.isPresent()) {
            //ResponseEntity.badRequest().build();
            return null;
        }

        playerService.deleteById(id);

        //return ResponseEntity.ok().build();
        return playerResourceAssembler.toResource(player.get());
    }
}
