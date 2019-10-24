package com.example.demo.controllers.AnonymousControllers;

import com.example.demo.assembler.PlayerResourceAssembler;
import com.example.demo.dtos.PlayerAnonDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.PlayerModel;
import com.example.demo.services.PlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("v1/anonymous")
public class AnonymousPlayerController {
    @Autowired
    private PlayerService playerService;
    @Autowired
    private PlayerResourceAssembler playerResourceAssembler;

    @GetMapping("/browse/player/{id}")
    public PlayerAnonDTO onePlayer(@PathVariable int id) {

        Optional<PlayerModel> playerModel = playerService.findById(id);
        if (playerModel.isPresent()) {

            return playerService.filteredPlayer(playerModel);

        }
        return null;

    }

}
