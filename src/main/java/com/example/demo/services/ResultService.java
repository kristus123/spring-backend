package com.example.demo.services;

import com.example.demo.dtos.ResultDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.MatchModel;
import com.example.demo.models.ResultModel;
import com.example.demo.models.TeamModel;
import com.example.demo.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
    ResultRepository resultRepository;

    @Autowired MatchService matchService;

    @Autowired TeamService teamService;

    public ResultModel save(ResultModel resultModel) {
        return resultRepository.save(resultModel);
    }

    private ResultModel convert(ResultDTO input) {
        Optional<MatchModel> match = matchService.findById(input.getMatchId());
        Optional<TeamModel> team = teamService.findById(input.getTeamId());

        if ( !match.isPresent() || !team.isPresent() )
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        return new ResultModel(match.get(), team.get(), input.getScore(), input.getResult());
    }

    public ResultModel create(ResultDTO input) throws ElementNotFoundException {
        ResultModel converted = convert(input);
        return save(converted);
    }

    public ResultModel update(Integer id, ResultDTO input) throws ElementNotFoundException {

        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find result with ID=" + id));

        ResultModel updatedResult = convert(input);
        updatedResult.setMatchId(id);
        return save(updatedResult);
    }

    public Optional<ResultModel> findById(Integer id) {
        return resultRepository.findById(id);
    }

    public List<ResultModel> findAll() {
        return resultRepository.findAll();
    }


    public ResultModel deleteById(Integer id) throws ElementNotFoundException {
        ResultModel result = findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find result with ID=" + id));
        resultRepository.deleteById(id);
        return result;
    }

}
