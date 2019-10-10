package com.example.demo.controllers.userControllers;

import com.example.demo.exceptions.MatchNotFoundException;
import com.example.demo.models.MatchModel;
import com.example.demo.services.MatchService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/user")
public class UserMatchController {

    @Autowired
    private MatchService matchService;


    @GetMapping("/get/match/{id}")
    public MatchModel oneMatch(@PathVariable Integer id) {

        Optional<MatchModel> match = matchService.findById(id);
        if (!match.isPresent())
            return null;

        return match.get();
    }

    @GetMapping("/get/match")
    public List<MatchModel> allMatches() {

        List<MatchModel> matches = matchService.findAll();

        return matches;
    }

}
