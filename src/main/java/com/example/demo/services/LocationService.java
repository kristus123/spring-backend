package com.example.demo.services;

import com.example.demo.dtos.LocationDTO;
import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.AddressModel;
import com.example.demo.models.LocationModel;
import com.example.demo.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LocationService {
    @Autowired LocationRepository locationRepository;

    @Autowired
    AddressService addressService;

    private LocationModel convert(LocationDTO input) {
        Optional<AddressModel> address = addressService.findById(input.getAddressId());

        if (!address.isPresent())
            return null;

        return new LocationModel(address.get(), input.getName(), input.getDescription());
    }

    public LocationModel create(LocationDTO input) throws ElementNotFoundException {

        LocationModel converted = convert(input);
        if (converted == null)
            throw new ElementNotFoundException("Could not locate one or several IDs in database");

        return save(converted);
    }

    public LocationModel save(LocationModel location) {
        return locationRepository.save(location);
    }

    public LocationModel update(Integer id, LocationDTO input) throws ElementNotFoundException {

        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find location with ID=" + id));

        LocationModel updatedLocation = convert(input);
        updatedLocation.setLocationId(id);
        return save(updatedLocation);
    }

    public LocationModel deleteById(Integer id) throws ElementNotFoundException {
        LocationModel location = findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find location with ID=" + id));
        locationRepository.deleteById(id);
        return location;
    }

    public Optional<LocationModel> findById(int id) { return locationRepository.findById(id); }

    public List<LocationModel> findAll() {return locationRepository.findAll(); }

}
