package com.example.demo.controllers.adminControllers;

import com.example.demo.models.MatchPositionId;
import com.example.demo.models.MatchPositionModel;
import com.example.demo.services.MatchPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Optional;

@RestController
@RequestMapping("/v1/admin")
public class AdministratorMatchPositionController {

    @Autowired
    MatchPositionService matchPositionService;

    @GetMapping("/get/matchPosition")
    public MatchPositionModel getMatchPosition(@RequestBody MatchPositionId matchPositionId) {
        Optional<MatchPositionModel> matchPositionModel = matchPositionService.findById(matchPositionId);
        if(matchPositionModel.isPresent()) {
            return matchPositionModel.get();
        }
        return null;
    }

    @PostMapping("post/matchPosition")
    public MatchPositionModel addMatchPosition(@RequestBody MatchPositionModel matchPositionModel) {
        System.out.println(matchPositionModel.getMatch());
        System.out.println(matchPositionModel.getPlayer());
        MatchPositionModel newMatchPosition = matchPositionService.save(matchPositionModel);
        return newMatchPosition;
    }

    @PutMapping("/update/matchPosition")
    public MatchPositionModel updateMatchPosition(@RequestBody MatchPositionModel matchPositionModel) {
        MatchPositionId matchPositionId = null;
        matchPositionId.setPlayer(matchPositionModel.getPlayer());
        matchPositionId.setMatch(matchPositionModel.getMatch());
        Optional<MatchPositionModel> oldMatchPosition = matchPositionService.findById(matchPositionId);
        if(oldMatchPosition.isPresent()) {
            MatchPositionModel updatedMatchPositionModel = matchPositionService.update(matchPositionModel, oldMatchPosition.get());
            return updatedMatchPositionModel;
        }
        return null;
    }

    @DeleteMapping("/delete/matchPosition")
    public MatchPositionModel deleteMatchPosition(@RequestBody MatchPositionId id) {
        Optional<MatchPositionModel> matchPositionModel = matchPositionService.findById(id);
        if(matchPositionModel.isPresent()) {
            MatchPositionModel tempMatchPosition = matchPositionModel.get();
            matchPositionService.delete(matchPositionModel.get());
            return tempMatchPosition;
        }
        return null;
    }
}
