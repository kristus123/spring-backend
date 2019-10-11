package com.example.demo.services;

import com.example.demo.interfaces.LivingHuman;
import com.example.demo.models.CoachModel;
import com.example.demo.models.OwnerModel;
import com.example.demo.models.PersonModel;
import com.example.demo.models.PlayerModel;
import com.example.demo.repositories.CoachRepository;
import com.example.demo.repositories.OwnerRepository;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.repositories.PlayerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class HumanService {
    @Autowired CoachRepository coachRepository;

    @Autowired PersonRepository personRepository;

    @Autowired PlayerRepository playerRepository;

    @Autowired OwnerRepository ownerRepository;

    public <T extends LivingHuman> void delete(T human) {

        PersonModel person = human.getPerson();


        Optional<CoachModel> possibleCoach = coachRepository.findByPerson(person);

        Optional<OwnerModel> possibleOwner = ownerRepository.findByPerson(person);

        Optional<PlayerModel> possiblePlayer = playerRepository.findByPerson(person);


        if (possibleCoach.isPresent()) {
            coachRepository.delete(possibleCoach.get());
        } else if ( possibleOwner.isPresent() ) {
            ownerRepository.delete(possibleOwner.get());
        } else if (possiblePlayer.isPresent()) {
            playerRepository.delete(possiblePlayer.get());
        } else {
            personRepository.delete(person);
        }
    }
}
