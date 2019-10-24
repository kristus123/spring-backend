package com.example.demo.repositories;

import com.example.demo.models.AddressModel;
import com.example.demo.models.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface LocationRepository extends JpaRepository<LocationModel, Integer> {

    Optional<LocationModel> findByName(String name);

    List<LocationModel> findByAddress(AddressModel address);

}
