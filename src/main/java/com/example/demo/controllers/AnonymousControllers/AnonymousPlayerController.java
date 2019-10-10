package com.example.demo.controllers.AnonymousControllers;

import com.example.demo.assembler.PlayerResourceAssembler;
import com.example.demo.controllers.PlayerController;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.models.PlayerModel;
import com.example.demo.services.PlayerService;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("v1/anonymous")
public class AnonymousPlayerController {
private PlayerService playerService;
private PlayerResourceAssembler playerResourceAssembler;

    @GetMapping("/browse/player/{id}")
    public Resource<PlayerModel> onePlayer(@PathVariable Integer id) {

        PlayerModel player = playerService.findById(id)
                .orElseThrow(() -> new PlayerNotFoundException(id));

        return playerResourceAssembler.toResource(player);
    }

}
