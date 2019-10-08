package com.example.demo.repositories;

import com.example.demo.models.LocationModel;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LocationRepository extends JpaRepository<LocationModel, Integer> {
}
