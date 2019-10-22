package com.example.demo.services;

import com.example.demo.dtos.PersonDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.*;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

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
        PersonModel person = findById(id)
                .orElseThrow(() -> new ElementNotFoundException("Could not find person with ID=" + id));

        // TODO PANDA: make sure you "delete" (inactivate) them from Coach / Owner / Player(?) tables as well

        coachService.findByPerson(person).ifPresent(coach -> coachService.deleteById(coach.getCoachId()));
        ownerService.findByPerson(person).ifPresent(owner -> ownerService.deleteById(owner.getOwnerId()));
        playerService.findByPerson(person).ifPresent(player -> playerService.deleteById(player.getPlayerId()));

        person.setActive(false);
        return personRepository.save(person);
    }

    public Optional<PersonModel> findById(Integer id) {
        Optional<PersonModel> person = personRepository.findById(id);
        if (!person.isPresent() || !person.get().isActive())
            return Optional.empty();
        return person;
    }

    public List<PersonModel> findAllActive() {
        return personRepository.findAll().stream().filter(person -> person.isActive()).collect(Collectors.toList());
    }

    public Optional<PersonModel> findByIdForced(Integer id) {
        return personRepository.findById(id);
    }

    public List<PersonModel> findAllForced() {
        return personRepository.findAll();
    }

    public PersonModel create(PersonModel personModel) {
        //System.out.println("saving in database + " + personModel);
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

