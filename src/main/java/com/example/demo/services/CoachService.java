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
import java.util.stream.Collectors;

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

    public CoachModel makePersonCoach(int personId) {

        PersonModel person = personService.findById(personId).orElseThrow(() -> new ElementNotFoundException("did not find user"));
        return makePersonCoach(person);
    }


    // TODO PANDA: vil finne alle Coaches som har eksistert ever? Eller bare aktive??
    public Optional<CoachModel> findByPersonId(int id) {
        return findAllActive().stream().filter(coach -> coach.getPerson().getPersonId() == id).findFirst();
    }

    private CoachModel convert(CoachDTO input) {
        Optional<PersonModel> person = personService.findById(input.getPersonId());

        if (!person.isPresent())
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        return new CoachModel(person.get());
    }

    public CoachModel save(CoachModel coachModel) {return coachRepository.save(coachModel);}

    public CoachModel create(CoachDTO input) throws ElementNotFoundException {
        CoachModel converted = convert(input);
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
        coach.setActive(false);
        return coachRepository.save(coach);
    }

    public Optional<CoachModel> findById(Integer id) {
        Optional<CoachModel> coach = coachRepository.findById(id);
        if (!coach.isPresent() || !coach.get().isActive())
            return Optional.empty();
        return coach;
    }

    public List<CoachModel> findAllActive() {
        return coachRepository.findAll().stream().filter(coach -> coach.isActive()).collect(Collectors.toList());
    }

    public Optional<CoachModel> findByIdForced(Integer id) {
        return coachRepository.findById(id);
    }

    public List<CoachModel> findAllForced() {
        return coachRepository.findAll();
    }

    public Optional<CoachModel> findByPerson(PersonModel personModel) {
        return coachRepository.findByPerson(personModel);
    }
}
