package com.example.demo.services;

import com.example.demo.models.MatchModel;
import com.example.demo.models.ResultModel;
import com.example.demo.repositories.MatchRepository;
import com.example.demo.repositories.ResultRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ResultService {

    @Autowired
  private ResultRepository resultRepository;

    public ResultModel save(ResultModel result) {
        return resultRepository.save(result);
    }
}
