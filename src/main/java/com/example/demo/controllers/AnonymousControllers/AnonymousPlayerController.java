package com.example.demo.controllers.AnonymousControllers;

import com.example.demo.assembler.PlayerResourceAssembler;
import com.example.demo.exceptions.ElementNotFoundException;
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
    public String onePlayer(@PathVariable Integer id) {

        PlayerModel player = playerService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find player with ID=" + id));
        return playerService.filteredPlayer(player);
    }

}
