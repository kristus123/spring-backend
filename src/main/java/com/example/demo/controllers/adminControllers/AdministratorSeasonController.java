package com.example.demo.controllers.adminControllers;

import com.example.demo.models.SeasonModel;
import com.example.demo.services.SeasonService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorSeasonController {


    @Autowired
    private SeasonService seasonService;


    @PostMapping("/post/season")
    public SeasonModel newSeason(@RequestBody SeasonModel seasonModel) {
        return seasonService.save(seasonModel);
    }

    @PutMapping("/update/season/{id}")
    public SeasonModel updateMatch(@PathVariable Integer id, @RequestBody SeasonModel seasonModel) {
        if (seasonModel.getSeasonId() != id || !seasonService.findById(id).isPresent()) {
            return null;
        }

        return seasonService.save(seasonModel);
    }

    @DeleteMapping("/delete/season/{id}")
    public SeasonModel deleteMatch(@PathVariable Integer id) {
        Optional<SeasonModel> season = seasonService.findById(id);
        if (!season.isPresent()) {
            return null;
        }

        seasonService.deleteById(id);

        return season.get();
    }
}
