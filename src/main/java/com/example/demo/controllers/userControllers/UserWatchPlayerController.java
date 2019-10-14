package com.example.demo.controllers.userControllers;

import com.example.demo.models.PlayerModel;
import com.example.demo.models.UserModel;
import com.example.demo.services.PlayerService;
import com.example.demo.services.UserService;
import com.example.demo.services.UserWatchPlayerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@RestController
@RequestMapping("/v1/user/watchlist")
public class UserWatchPlayerController {

    @Autowired
    UserService userService;

    @Autowired
    PlayerService playerService;

    @GetMapping("/get/player/{id}")
    PlayerModel getPlayer(@PathVariable Integer id) {
        UserModel user = userService.getUser();
        Optional<PlayerModel> player = user.getPlayers().stream().filter(p -> p.getPlayerId() == id).findFirst();
        if (player.isPresent())
            return player.get();
        return null;
    }

    @GetMapping("/get/player")
    Set<PlayerModel> getPlayers() {
        UserModel user = userService.getUser();
        return user.getPlayers();
    }

    @PostMapping("/post/player")
    PlayerModel addPlayer(@RequestBody PlayerModel player) {
        UserModel user = userService.getUser();
        if (user.getPlayers().add(player))
            return player;
        return null;
    }

    // updating fav player equals to removing that player and replacing it with (adding) another player...
    @PutMapping("/update/player/{id}")
    PlayerModel updatePlayer(@PathVariable Integer id, @RequestBody Integer otherId) {

        if (otherId == id)
            return null;

        Optional<PlayerModel> existingPlayer = playerService.findById(id);
        if (!existingPlayer.isPresent())
            return null;

        Optional<PlayerModel> otherPlayer = playerService.findById(otherId);
        if (!otherPlayer.isPresent())
            return null;

        UserModel user = userService.getUser();
        Set<PlayerModel> players = user.getPlayers();
        if (players.contains(existingPlayer)) {
            players.remove(existingPlayer);
        }

        players.add(otherPlayer.get());
        return otherPlayer.get();
    }

    @DeleteMapping("/delete/player/{id}")
    PlayerModel deletePlayer(@PathVariable Integer id) {

        Optional<PlayerModel> existingPlayer = playerService.findById(id);
        if (!existingPlayer.isPresent())
            return null;

        UserModel user = userService.getUser();
        if (!user.getPlayers().remove(existingPlayer.get()))
            return null;

        return existingPlayer.get();
    }
}
