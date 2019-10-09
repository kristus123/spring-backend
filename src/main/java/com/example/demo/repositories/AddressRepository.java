package com.example.demo.repositories;

import com.example.demo.models.AddressModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface AddressRepository extends JpaRepository<AddressModel, Integer> {

    Optional<AddressModel> findByAddresses(String... addresses);
}
