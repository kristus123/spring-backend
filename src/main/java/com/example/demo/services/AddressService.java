package com.example.demo.services;

import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.AddressModel;
import com.example.demo.models.LocationModel;
import com.example.demo.models.PersonModel;
import com.example.demo.repositories.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired AssociationRepository associationRepository;


    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    LocationRepository locationRepository;

    public Optional<AddressModel> findById(int id) {
        return addressRepository.findById(id);
    }

    public List<AddressModel> findAll() {return addressRepository.findAll();}



    public AddressModel save(AddressModel addressModel) {
        return addressRepository.save(addressModel);
    }


    public AddressModel deleteById(int addressId) {
        AddressModel address = addressRepository.findById(addressId)
                .orElseThrow(() -> new ElementNotFoundException("Could not find address with ID=" + addressId));

        Optional< List<PersonModel> > person = personRepository.findByAddress(address);
        if (person.isPresent()) {
            person.get().forEach(p -> {
                p.setAddress(null);
                personRepository.save(p);
            });
        }

        List<LocationModel> locations = locationRepository.findByAddress(address);
        locations.forEach(location -> {
            location.setAddress(null);
            locationRepository.save(location);
        });

        addressRepository.delete(address);
        return address;
    }

    public boolean deleteAll() {
        findAll().forEach(addr -> deleteById(addr.getAddressId()));
        return findAll().isEmpty();
    }

    public AddressModel update(Integer id, AddressModel address) throws ElementNotFoundException {
        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find address with ID=" + id));
        address.setAddressId(id);
        return save(address);
    }

    public AddressModel createAddress(AddressModel addressModel) {
        return addressRepository.save(addressModel);
    }
}
