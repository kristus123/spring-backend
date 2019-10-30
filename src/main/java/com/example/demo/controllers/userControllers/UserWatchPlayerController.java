package com.example.demo.controllers.userControllers;

import com.example.demo.assembler.UserPlayerResourceAssembler;
import com.example.demo.dtos.UserPlayerDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.PlayerModel;
import com.example.demo.models.UserModel;
import com.example.demo.services.PlayerService;
import com.example.demo.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.hateoas.Resources;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;
import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

@RestController
@RequestMapping("/v1/user/watchlist")
public class UserWatchPlayerController {


    @Autowired
    UserService userService;

    @Autowired
    PlayerService playerService;

    @Autowired
    UserPlayerResourceAssembler assembler;

    @GetMapping("/get/player/{id}")
    public ResponseEntity<Resource<PlayerModel>> getPlayer(@PathVariable Integer id, Principal principal) {

        /*
        UserModel user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ElementNotFoundException("Could not find user with username=" + principal.getName()));
         */
        UserModel user = userService.findById(1);
        if (user == null) throw new ElementNotFoundException("Could not find user with ID=1");

        PlayerModel player = playerService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find player with ID=" + id));

        if (!user.getPlayers().contains(player))
            throw new ElementNotFoundException("Player with ID=" + id + " is not present in watchlist");

        assembler.setPrincipal(principal);
        Resource<PlayerModel> resource = assembler.toResource(player);

        return ResponseEntity
                .ok(resource);
    }

    @GetMapping("/get/player")
    public ResponseEntity<Resources<Resource<PlayerModel>>> getPlayers(Principal principal) {

        /*
        UserModel user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ElementNotFoundException("Could not find user with username=" + principal.getName()));
         */
        UserModel user = userService.findById(1);
        if (user == null) throw new ElementNotFoundException("Could not find user with ID=1");

        List<Resource<PlayerModel>> players = user.getPlayers()
                .stream()
                .map(assembler::toResource)
                .collect(Collectors.toList());

        if (players.isEmpty())
            throw new ElementNotFoundException("No players in watchlist");

        return ResponseEntity
                .ok(new Resources<>(players,
                        linkTo(methodOn(UserWatchPlayerController.class).getPlayers(principal)).withSelfRel()));

    }

    @PostMapping("/post/player")
    public ResponseEntity<Resource<PlayerModel>> addPlayer(@RequestBody UserPlayerDTO dto, Principal principal) throws URISyntaxException {

        /*
        UserModel user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ElementNotFoundException("Could not find user with username=" + principal.getName()));
         */
        UserModel user = userService.findById(1);
        if (user == null) throw new ElementNotFoundException("Could not find user with ID=1");

        PlayerModel player = playerService.findById(dto.getPlayerId())
                .orElseThrow(() -> new ElementNotFoundException("Player with ID=" + dto.getPlayerId() + " does not exist"));

        // Add player to watchlist
        if(!user.addPlayer(player))
            return null;

        userService.save(user);
        playerService.save(player);

        assembler.setPrincipal(principal);
        Resource<PlayerModel> resource = assembler.toResource(player);

        return ResponseEntity
                .created(new URI(resource.getId().expand().getHref()))
                .body(resource);
    }

    // updating fav player equals to removing that player and replacing it with (adding) another player...
    // doesn't make sense to allow this operation for a User


    @DeleteMapping("/delete/player/{id}")
    public ResponseEntity<PlayerModel> deletePlayer(@PathVariable Integer id, Principal principal) {

        PlayerModel player = playerService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Player with ID=" + id + " does not exist"));

        /*
        UserModel user = userService.findByUsername(principal.getName())
                .orElseThrow(() -> new ElementNotFoundException("Could not find user with username=" + principal.getName()));
         */
        UserModel user = userService.findById(1);
        if (user == null) throw new ElementNotFoundException("Could not find user with ID=1");

        if (!user.deletePlayer(player))
            return null;

        userService.save(user);
        playerService.save(player);

        return ResponseEntity.ok(player);
    }

}
