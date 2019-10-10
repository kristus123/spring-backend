package com.example.demo.controllers.userControllers;

import com.example.demo.exceptions.SeasonNotFoundException;
import com.example.demo.models.SeasonModel;
import com.example.demo.services.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/v1/user")
public class UserSeasonController {

    @Autowired
    private SeasonService seasonService;


    @GetMapping("/get/season/{id}")
    public SeasonModel oneSeason(@PathVariable Integer id) {

        SeasonModel season = seasonService.findById(id)
                .orElseThrow(() -> new SeasonNotFoundException(id));

        return season;
    }

    @GetMapping("/get/season")
    public List<SeasonModel> allSeasons() {

        List<SeasonModel> seasons = seasonService.findAll();

        return seasons;
    }

}
