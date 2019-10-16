package com.example.demo.controllers.commonControllers;

import com.example.demo.models.MatchPositionId;
import com.example.demo.models.MatchPositionModel;
import com.example.demo.services.MatchPositionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/v1/common")
public class CommonMatchPositionController {

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

    @GetMapping("/get/matchposition")
    public List<MatchPositionModel> getMatchPositions() {

        List<MatchPositionModel> matchPositions = matchPositionService.findAll();

        if (matchPositions.isEmpty())
            return null;

        return matchPositions;
    }
}