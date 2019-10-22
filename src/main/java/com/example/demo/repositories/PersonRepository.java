package com.example.demo.repositories;

import com.example.demo.models.AddressModel;
import com.example.demo.models.PersonModel;
import com.example.demo.models.UserModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PersonRepository extends JpaRepository<PersonModel, Integer> {
    Optional<PersonModel> findByFirstName(String firstName);

    Optional<List<PersonModel>> findByAddress(AddressModel addressModel);
}
