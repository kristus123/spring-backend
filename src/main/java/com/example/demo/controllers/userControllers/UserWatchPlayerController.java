package com.example.demo.controllers.userControllers;

import com.example.demo.models.PlayerModel;
import com.example.demo.models.UserModel;
import com.example.demo.models.UserWatchPlayerModel;
import com.example.demo.services.PlayerService;
import com.example.demo.services.UserService;
import com.example.demo.services.UserWatchPlayerService;
import com.example.demo.services.userAuth.UserDetailsServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.security.Principal;
import java.util.List;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/v1/user/watchlist")
public class UserWatchPlayerController {

    @Autowired
    UserService userService;

    @Autowired
    PlayerService playerService;


    @GetMapping("/get/player/{id}")
    PlayerModel getPlayer(@PathVariable Integer id, Principal principal) {
        //UserModel user = userService.findByUsername(principal.getName()).get();
        Optional<UserModel> user = userService.findByUsername(principal.getName());
        if (!user.isPresent())
            return null;

        Optional<PlayerModel> player = playerService.findById(id);
        if (!player.isPresent())
            return null;

        if (!user.get().getPlayers().contains(player.get()))
            return null;

        System.out.println("TEST: successfully retrieved player from watchlist");
        return player.get();
    }

    @GetMapping("/get/player")
    Set<PlayerModel> getPlayers(Principal principal) {
        Optional<UserModel> user = userService.findByUsername(principal.getName());
        if (!user.isPresent())
            return null;

        Set<PlayerModel> players = user.get().getPlayers();
        if (players.isEmpty())
            System.out.println("TEST: no players found");
        else
            System.out.println("TEST: successfully retrieved players from watchlist");

        return players;
    }

    @PostMapping("/post/player")
    PlayerModel addPlayer(@RequestBody PlayerModel player, Principal principal) {
        Optional<UserModel> user = userService.findByUsername(principal.getName());
        UserModel userModel = userService.getMe(principal);

        // User exists?
        if (!user.isPresent())
            return null;

        // Player exists?
        if (!playerService.findById(player.getPlayerId()).isPresent()) {
            return null;
        }

        // Add player to watchlist
        if(!user.get().addPlayer(player))
            return null;

        userService.save(user.get());
        playerService.save(player);

        System.out.println("TEST: players in user");
        System.out.println(userModel.getPlayers());
        return player;
    }


    // updating fav player equals to removing that player and replacing it with (adding) another player...
    @PutMapping("/update/player/{id}")
    PlayerModel updatePlayer(@PathVariable Integer id, @RequestBody Integer otherId, Principal principal) {

        if (otherId == id)
            return null;

        // User exists?
        Optional<UserModel> user = userService.findByUsername(principal.getName());
        if (!user.isPresent())
            return null;

        // Players exist?

        Optional<PlayerModel> existingPlayer = playerService.findById(id);
        if (!existingPlayer.isPresent())
            return null;

        Optional<PlayerModel> otherPlayer = playerService.findById(otherId);
        if (!otherPlayer.isPresent())
            return null;


        if (!user.get().deletePlayer(existingPlayer.get()))
            return null;

        if (!user.get().addPlayer(otherPlayer.get()))
            return null;

        userService.save(user.get());
        playerService.save(otherPlayer.get());

        System.out.println("TEST: updated player successfully");
        return otherPlayer.get();
    }



    @DeleteMapping("/delete/player/{id}")
    PlayerModel deletePlayer(@PathVariable Integer id, Principal principal) {

        Optional<PlayerModel> player = playerService.findById(id);
        if (!player.isPresent())
            return null;

        Optional<UserModel> user = userService.findByUsername(principal.getName());
        if (!user.isPresent())
            return null;

        if (!user.get().deletePlayer(player.get()))
            return null;

        userService.save(user.get());
        playerService.save(player.get());

        System.out.println("TEST: successfully deleted player from watchlist");

        return player.get();
    }

}
