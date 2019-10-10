package com.example.demo.controllers.adminControllers;

import com.example.demo.models.MatchModel;
import com.example.demo.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController @RequestMapping("/v1/admin")
public class AdministratorMatchController {

    @Autowired
    MatchService matchService;

    @GetMapping("/get/match/{matchId}")
    public MatchModel getMatch(@PathVariable int matchId) {
        Optional<MatchModel> match = matchService.findById(matchId);
        if(match.isPresent()) {
            return  match.get();
        }
        return null;
    }

    @PostMapping("/post/match")
    public MatchModel postMatch(@RequestBody MatchModel matchModel) {
        MatchModel newMatch = matchService.save(matchModel);
        return  newMatch;
    }
}
