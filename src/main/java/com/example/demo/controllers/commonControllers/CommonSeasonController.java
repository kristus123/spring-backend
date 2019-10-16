package com.example.demo.controllers.commonControllers;

import com.example.demo.exceptions.SeasonNotFoundException;
import com.example.demo.models.SeasonModel;
import com.example.demo.services.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/common")
public class CommonSeasonController {

    @Autowired
    private SeasonService seasonService;


    @GetMapping("/get/season/{id}")
    public SeasonModel getSeason(@PathVariable Integer id) {

        Optional<SeasonModel> season = seasonService.findById(id);
        if(!season.isPresent())
            return null;

        return season.get();
    }

    @GetMapping("/get/season")
    public List<SeasonModel> getSeasons() {

        List<SeasonModel> seasons = seasonService.findAll();

        if (seasons.isEmpty())
            return null;

        return seasons;
    }

}
