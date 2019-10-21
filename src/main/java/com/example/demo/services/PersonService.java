package com.example.demo.services;

import com.example.demo.dtos.PersonDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class PersonService {

    @Autowired PersonRepository personRepository;

    @Autowired OwnerService ownerService;

    @Autowired AddressService addressService;

    @Autowired TeamRepository teamRepository;

    @Autowired CoachService coachService;

    @Autowired AddressRepository addressRepository;

    @Autowired CoachRepository coachRepository;

    @Autowired PlayerRepository playerRepository;

    @Autowired PlayerService playerService;

    private PersonModel convert(PersonDTO input) {
        Optional<AddressModel> address = addressService.findById(input.getAddressId());

        if (!address.isPresent())
            return null;

        return new PersonModel(
                input.getFirstName(),
                input.getLastName(),
                input.getDateOfBirth(),
                address.get()
        );
    }

    public PersonModel save(PersonModel personModel) {
        return personRepository.save(personModel);
    }

    public PersonModel create(PersonDTO input) {

        PersonModel converted = convert(input);
        if (converted == null)
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        return save(converted);
    }

    public Optional<PersonModel> findByFirstName(String firstName) {
        return personRepository.findByFirstName(firstName);
    }


    public PersonModel update(Integer id, PersonDTO input) throws ElementNotFoundException {

        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find person with ID=" + id));

        PersonModel updatedPerson = convert(input);
        updatedPerson.setPersonId(id);
        return save(updatedPerson);

    }


    public PersonModel deleteById(Integer id) throws ElementNotFoundException {
        //slett andre ting
        PersonModel personModel = findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find person with ID=" + id));

        Optional<CoachModel> coach = coachRepository.findByPerson(personModel);
        Optional<OwnerModel> owner = ownerService.findByPerson(personModel);

        if (owner.isPresent()) {
            ownerService.delete(owner.get());
            return null; // TODO PANDA: hvorfor har vi dette?
        }

        if (coach.isPresent()) {
            System.out.println("COACH ER PRESENT");
            coachService.deleteById(coach.get().getCoachId());
            return null; // TODO PANDA: hvorfor har vi dette?

        }
        personRepository.deleteById(id);
        return personModel;

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

