package com.example.demo.services;

import com.example.demo.dtos.CoachDTO;
import com.example.demo.exceptions.ElementNotFoundException;
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
    CoachRepository coachRepository;

    @Autowired
    PersonService personService;

    public CoachModel makePersonCoach(PersonModel person) {
        Optional<CoachModel> coach =  coachRepository.findByPerson(person);
        if (coach.isPresent()) {
            return coach.get();
        } else {
            return save(new CoachModel(person));
        }

    }

    private CoachModel convert(CoachDTO input) {
        Optional<PersonModel> person = personService.findById(input.getPersonId());

        if (!person.isPresent())
            return null;

        return new CoachModel(person.get());
    }

    public CoachModel save(CoachModel coachModel) {return coachRepository.save(coachModel);}

    public CoachModel create(CoachDTO input) throws ElementNotFoundException {

        CoachModel converted = convert(input);
        if (converted == null)
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        return save(converted);
    }

    public CoachModel update(Integer id, CoachDTO input)  throws ElementNotFoundException {
        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find coach with ID=" + id));

        CoachModel updatedCoach = convert(input);
        updatedCoach.setCoachId(id);
        return save(updatedCoach);
    }


    public CoachModel deleteById(Integer id) {
        CoachModel coach = findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find coach with ID=" + id));
        coachRepository.deleteById(id);
        return coach;
    }

    public Optional<CoachModel> findById(Integer id) {return coachRepository.findById(id);}
    public List<CoachModel> findAll() {return coachRepository.findAll();}
}
