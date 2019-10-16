package com.example.demo.controllers.adminControllers;

import com.example.demo.models.MatchModel;
import com.example.demo.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping ("/v1/admin")
public class AdministratorMatchController {


    @Autowired
    private MatchService matchService;


    @PostMapping ("/post/match")
    public MatchModel newMatch(@RequestBody MatchModel matchModel) {
        return matchService.save(matchModel);
    }

    /*
    A method to create a match somewhat simplified.
    Postman complained when i tried to create a new Match (a 'POST' first to test a 'GET').
    On Kristian's advice I created DTO's for LocalDate, Season and Team.
    Didn't work quite as planned. However this was to test anonymous end points, which is not really necessary,
    because I think the anonymous code is good.
    @PostMapping ("/post/match/{id}")
    public MatchModel newMatch(@PathVariable Integer id) {}

       // MatchModel newMatch = new MatchModel(LocalDate, TeamModel homeTeamDTO, TeamModel awayTeamDTO, SeasonModel seasonDTO, );
*/

    @PutMapping ("/update/match/{id}")
    public MatchModel updateMatch(@PathVariable Integer id, @RequestBody MatchModel matchModel) {
        if (matchModel.getMatchId() != id || !matchService.findById(id).isPresent()) {
            return null;
        }

        return matchService.save(matchModel);
    }

    @DeleteMapping ("/delete/match/{id}")
    public MatchModel deleteMatch(@PathVariable Integer id) {
        Optional<MatchModel> match = matchService.findById(id);
        if (!match.isPresent()) {
            return null;
        }

        matchService.deleteById(id);

        return match.get();
    }

    @GetMapping ("/get/match/{matchId}")
    public MatchModel getMatch(@PathVariable int matchId) {
        Optional<MatchModel> match = matchService.findById(matchId);
        if (match.isPresent()) {
            return match.get();
        }
        return null;
    }
}
