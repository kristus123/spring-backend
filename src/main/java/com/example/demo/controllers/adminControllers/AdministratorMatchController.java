package com.example.demo.controllers.adminControllers;

import com.example.demo.models.MatchModel;
import com.example.demo.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorMatchController {


    @Autowired
    private MatchService matchService;


    @PostMapping("/post/match")
    public MatchModel addMatch(@RequestBody MatchModel matchModel) {
        return matchService.save(matchModel);
    }

    @PutMapping("/update/match/{id}")
    public MatchModel updateMatch(@PathVariable Integer id, @RequestBody MatchModel matchModel) {
        if (matchModel == null || matchModel.getMatchId() != id) {
            return null;
        }

        Optional<MatchModel> oldMatch = matchService.findById(id);
        if (!oldMatch.isPresent()) {
            return null;
        }

        return matchService.update(matchModel, oldMatch.get());
    }

    @DeleteMapping("/delete/match/{id}")
    public MatchModel deleteMatch(@PathVariable Integer id) {
        Optional<MatchModel> match = matchService.findById(id);
        if (!match.isPresent()) {
            return null;
        }

        matchService.deleteById(id);

        return match.get();
    }

}
