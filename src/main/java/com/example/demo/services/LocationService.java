package com.example.demo.services;

import com.example.demo.models.LocationModel;
import com.example.demo.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class LocationService {
    @Autowired LocationRepository locationRepository;

    public LocationModel save(LocationModel location) {
        return locationRepository.save(location);
    }

    public List<LocationModel> findAll() {return locationRepository.findAll(); }
}
