package com.example.demo.services;

import com.example.demo.models.CoachModel;
import com.example.demo.models.PersonModel;
import com.example.demo.repositories.CoachRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class CoachService {

    @Autowired
    private CoachRepository coachRepository;

    public CoachModel makePersonCoach(PersonModel person) {
        Optional<CoachModel> coach =  coachRepository.findByPerson(person);
        if (coach.isPresent()) {
            return coach.get();
        } else {
            return save(new CoachModel(person));
        }

    }

    public Optional<CoachModel> findByPersonId(int id) {
        return findAll().stream().filter(coach -> coach.getPerson().getPersonId() == id).findFirst();
    }


    public CoachModel save(CoachModel coachModel) {return coachRepository.save(coachModel);}
    public CoachModel update(Integer id, CoachModel coachModel) {
        if(!findById(id).isPresent())
            return null;
        coachModel.setCoachId(id);
        return save(coachModel);
    }
    public void delete(Integer id) {
        Optional<CoachModel> coach = coachRepository.findById(id);
        if (coach.isPresent()) {
            System.out.println("COAch IS HERE");
        }

        coachRepository.deleteById(id);
    }
    public Optional<CoachModel> findById(Integer id) {return coachRepository.findById(id);}
    public List<CoachModel> findAll() {return coachRepository.findAll();}
}
