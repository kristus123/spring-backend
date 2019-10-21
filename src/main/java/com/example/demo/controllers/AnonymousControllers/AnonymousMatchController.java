package com.example.demo.controllers.AnonymousControllers;

import com.example.demo.assembler.MatchResourceAssembler;
import com.example.demo.controllers.MatchController;
import com.example.demo.dtos.MatchResultDTO;
import com.example.demo.exceptions.MatchNotFoundException;
import com.example.demo.exceptions.PlayerNotFoundException;
import com.example.demo.models.MatchModel;
import com.example.demo.models.PlayerModel;
import com.example.demo.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Resource;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.Optional;

@RestController
@RequestMapping("/v1/anonymous")
public class AnonymousMatchController {
    @Autowired
    private MatchService matchService;


    @GetMapping("/browse/match/{id}")
    public MatchResultDTO oneMatch(@PathVariable int id) {
        Optional<MatchModel> match = matchService.findById(id);
        if (match.isPresent()) {
            return matchService.getFilteredMatchStats(match);

        }
        return null;

    }

}
