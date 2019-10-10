package com.example.demo.services;

import com.example.demo.models.CoachModel;
import com.example.demo.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;

    public CoachModel save(CoachModel coachModel) {return coachRepository.save(coachModel);}
    public CoachModel update(Integer id, CoachModel coachModel) {
        if(!coachRepository.findById(id).isPresent())
            return null;
        return coachRepository.save(coachModel);
    }
    public void delete(Integer id) {coachRepository.deleteById(id);}
    public Optional<CoachModel> findById(Integer id) {return coachRepository.findById(id);}
    public List<CoachModel> findAll() {return coachRepository.findAll();}
}
