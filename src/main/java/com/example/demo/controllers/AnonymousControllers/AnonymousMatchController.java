package com.example.demo.controllers.AnonymousControllers;

import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.dtos.MatchResultDTO;
import com.example.demo.models.MatchModel;
import com.example.demo.services.MatchService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/v1/anonymous")
public class AnonymousMatchController {

    private MatchService matchService;


    @GetMapping("/browse/match/{id}")
    public MatchResultDTO oneMatch(@PathVariable Integer id) {

        MatchModel match = matchService.findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find match with ID=" + id));

        return matchService.getFilteredMatchStats(match);

    }

}
