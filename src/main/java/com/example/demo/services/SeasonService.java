package com.example.demo.services;

import com.example.demo.models.SeasonModel;
import com.example.demo.repositories.SeasonRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class SeasonService {

    @Autowired
    private SeasonRepository seasonRepository;

    public SeasonModel save(SeasonModel season) {
        return seasonRepository.save(season);
    }

    public void delete(SeasonModel season) {
        seasonRepository.delete(season);
    }

    public Optional<SeasonModel> findById(Integer id) {
        return seasonRepository.findById(id);
    }

    public List<SeasonModel> findAll() {
        return seasonRepository.findAll();
    }
}
