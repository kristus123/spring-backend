package com.example.demo;

import com.example.demo.models.*;
import com.example.demo.repositories.TeamRepository;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.sql.Date;
import java.time.LocalDate;

@Configuration
@Slf4j
public class LoadDatabase {

    @Bean
    CommandLineRunner initDatabase(TeamRepository teamRepository) {

        return args -> {
            teamRepository.save(new TeamModel(
                    new AssociationModel("ManU", "some very long and fancy description woop"),
                    new CoachModel(
                            new PersonModel(
                            "Panda",
                            "Po",
                            Date.valueOf(LocalDate.now()),
                            new AddressModel(
                                    "Superkul vei 1",
                                    "kulere adresse",
                                    "superhemmelig",
                                    10000,
                                    "Tokyo",
                                    "Japan"
                            ))),
                    new OwnerModel(
                            new PersonModel(
                            "Master",
                            "Shifu",
                            Date.valueOf(LocalDate.now()),
                            new AddressModel(
                                    "Shanghai vei 1",
                                    "kulere adresse",
                                    "superhemmelig",
                                    10000,
                                    "Shanghai",
                                    "Kina"
                            ))),
                    new LocationModel(
                            new AddressModel(
                                    "Bislett Stadion",
                                    "ved noroff",
                                    "og experis",
                                    10000,
                                    "Oslo",
                                    "Norge"
                            ),
                            "Bislett Stadion",
                            "ligger sentralt og er stooooooor"
                    )
            ));

        };
    }
}
