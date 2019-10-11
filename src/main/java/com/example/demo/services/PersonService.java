package com.example.demo.services;

import com.example.demo.exceptions.PersonNotFoundException;
import com.example.demo.models.CoachModel;
import com.example.demo.models.OwnerModel;
import com.example.demo.models.PersonModel;
import com.example.demo.models.TeamModel;
import com.example.demo.repositories.CoachRepository;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.repositories.TeamRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    OwnerService ownerService;

    @Autowired TeamRepository teamRepository;

    @Autowired CoachService coachService;

    @Autowired
    CoachRepository coachRepository;

    public PersonModel save(PersonModel personModel) { return personRepository.save(personModel);}
    public PersonModel update(Integer id, PersonModel personModel) {
        if(!personRepository.findById(id).isPresent())
            return null;
        return personRepository.save(personModel);

    }
    public void delete(Integer id) {

        //slett andre ting
        Optional<PersonModel> personModel = personRepository.findById(id);
        if (personModel.isPresent()) {
            System.out.println("DEN ER PRESENT" + personModel.isPresent());
            Optional<CoachModel> coach = coachRepository.findByPerson(personModel.get());
            Optional<OwnerModel> owner = ownerService.findByPerson(personModel.get());

            if (owner.isPresent()) {
                ownerService.delete(owner.get());
                return;
            }

            if (coach.isPresent()) {
                System.out.println("COACH ER PRESENT");
                coachService.delete(coach.get().getCoachId());
                return ;

            }
            delete(personModel.get().getPersonId());

        }
        else { System.out.println("user not found"); }

    }

    public Optional<PersonModel> findById(Integer id) {return personRepository.findById(id);}

    public List<PersonModel> findAll() {return personRepository.findAll();}

    public PersonModel create(PersonModel personModel) {
        System.out.println("saving in database + " + personModel);
        personRepository.save(personModel);
        return personModel;
    }





    public PersonModel makePersonOwnerOf(PersonModel person, TeamModel team) {
        Optional<OwnerModel> owner = ownerService.findByPerson(person);
        if (owner.isPresent()) {
            team.setOwner(owner.get());
        } else {
            team.setOwner(ownerService.create(person));
        }
        teamRepository.save(team);

        return person;
    }

}
