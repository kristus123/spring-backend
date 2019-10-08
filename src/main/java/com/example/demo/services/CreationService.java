package com.example.demo.services;

import com.example.demo.models.AddressModel;
import com.example.demo.models.LocationModel;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.repositories.LocationRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class CreationService {
    @Autowired
    AddressRepository addressRepository;

    @Autowired LocationRepository locationRepository;


    public AddressModel createAddress(AddressModel addressModel) {
        return addressRepository.save(addressModel);
    }

    public AddressModel createAddress(int postalCode, String city, String country, String... addresses) {

        return addressRepository.save(new AddressModel(postalCode, city, country, addresses));
    }


    public LocationModel createLocation(AddressModel address, String name, String description) {

        return locationRepository.save(new LocationModel(address, name, description));
    }

    public LocationModel createLocation(LocationModel locationModel) {
        return locationRepository.save(locationModel);
    }

}
