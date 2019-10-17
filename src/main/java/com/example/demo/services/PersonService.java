package com.example.demo.services;

import com.example.demo.exceptions.PersonNotFoundException;
import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired PersonService personService;

    @Autowired
    private PersonRepository personRepository;

    @Autowired
    OwnerService ownerService;

    @Autowired TeamRepository teamRepository;

    @Autowired CoachService coachService;

    @Autowired
    AddressRepository addressRepository;

    @Autowired
    CoachRepository coachRepository;

    @Autowired
    PlayerRepository playerRepository;

    @Autowired PlayerService playerService;

    public PersonModel save(PersonModel personModel) {
        if (personModel.getAddress() != null) addressRepository.save(personModel.getAddress());

        return personRepository.save(personModel);
    }

    public Optional<PersonModel> findByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName);
    }


    public PersonModel update(Integer id, PersonModel personModel) {
        if(!findById(id).isPresent())
            return null;
        // This must be set if the id is not set in the json
        personModel.setPersonId(id);
        return save(personModel);

    }


    public PersonModel delete(Integer id) {
        //slett andre ting
        Optional<PersonModel> personModel = personRepository.findById(id);
        if (personModel.isPresent()) {
            System.out.println("DEN ER PRESENT" + personModel.isPresent());
            Optional<CoachModel> coach = coachRepository.findByPerson(personModel.get());
            Optional<OwnerModel> owner = ownerService.findByPerson(personModel.get());

            if (owner.isPresent()) {
                ownerService.delete(owner.get());
                return null;
            }

            if (coach.isPresent()) {
                System.out.println("COACH ER PRESENT");
                coachService.delete(coach.get().getCoachId());
                return null;

            }
            personRepository.deleteById(id);
            return personModel.get();

        }
        else {
            System.out.println("user not found");
            return null;
        }

    }

    public Optional<PersonModel> findById(Integer id) {return personRepository.findById(id);}

    public List<PersonModel> findAll() {
        List<PersonModel> liste = personRepository.findAll();
        liste.forEach(System.out::println);
        return liste;
    }

    public PersonModel create(PersonModel personModel) {
        System.out.println("saving in database + " + personModel);
        personRepository.save(personModel);
        return personModel;
    }

    public TeamModel makePersonOwnerOf(int personId, int teamId) {
        Optional<PersonModel> person = personRepository.findById(personId);
        Optional<TeamModel> team = teamRepository.findById(teamId);

        if (person.isPresent() && team.isPresent()) return makePersonOwnerOf(person.get(), team.get());

        else return null;
    }

    public TeamModel makePersonOwnerOf(PersonModel person, TeamModel team) {
        Optional<OwnerModel> owner = ownerService.findByPerson(person);
        if (owner.isPresent()) {
            team.setOwner(owner.get());
        } else {
            System.out.println("not a coach from before, making him a coach now");
            team.setOwner(ownerService.create(person));
        }
        return teamRepository.save(team);
    }


    public CoachModel makePersonCoachOf(PersonModel person, TeamModel team) {
        CoachModel coach = coachService.makePersonCoach(person);
        team.setCoach(coach);
        teamRepository.save(team);
        return coach;
    }

    public CoachModel makePersonCoachOf(int personId, int teamId) {
        Optional<PersonModel> person = findById(personId);
        Optional<TeamModel>   team   = teamRepository.findById(teamId);

        if (!person.isPresent() || !team.isPresent()) return null;

        return makePersonCoachOf(person.get(), team.get());

    }

    public PlayerModel makePersonPlayerOf(PersonModel person, TeamModel team) {
        return makePersonPlayerOf(person.getPersonId(), team.getTeamId());
    }

    public PlayerModel makePersonPlayerOf(int personId, int teamId) {
        Optional<PersonModel> person = personRepository.findById(personId);
        Optional<TeamModel> team = teamRepository.findById(teamId);

        if (!person.isPresent() && !team.isPresent()) return null;
        Optional<PlayerModel> player =  playerRepository.findByPerson(person.get());

        if (player.isPresent()) {
            player.get().setTeam(team.get());
            return player.get();
        } else {
            return playerService.save(new PlayerModel(person.get(), team.get(), "N/A", "N/A", person.get().getFirstName()));
        }



    }

}

