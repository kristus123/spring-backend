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

    public boolean delete(int addressId) {
        Optional<AddressModel> address = addressRepository.findById(addressId);

        if (address.isPresent()) {
            Optional< List<PersonModel> > person = personRepository.findByAddress(address.get());
            if (person.isPresent()) {
                person.get().forEach(p -> {
                    p.setAddress(null);
                    personRepository.save(p);
                });

            }

            List<LocationModel> locations = locationRepository.findByAddress(address.get());
            locations.forEach(location -> {
                location.setAddress(null);
                locationRepository.save(location);
            });

            addressRepository.delete(address.get());
            return true;
        }
        throw new RuntimeException("address Id not found");
    }

    public AddressModel update(Integer id, AddressModel address) throws ElementNotFoundException {
        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find address with ID=" + id));
        address.setAddressId(id);
        return save(address);
    }

    //KAN DENNE SLETTES //DENNE VIL LITTERALLY IKKE FUNGERE. BRU KHELLER METODEN OVER
    // JEG ER LAT OG HAR IKKE LYST Å ENDRE ALLE STEDENE HVOR DENNE BLIR BRUKT
    public AddressModel deleteById(Integer id) throws ElementNotFoundException { //KAN DENNE SLETTES
        AddressModel address = findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find address with ID=" + id)); //KAN DENNE SLETTES
        //KAN DENNE SLETTES
        //KAN DENNE SLETTES
        //KAN DENNE SLETTES
        //KAN DENNE SLETTES
        //KAN DENNE SLETTES
        addressRepository.deleteById(id); //KAN DENNE SLETTES
        return address; //KAN DENNE SLETTES
    }

    public AddressModel createAddress(AddressModel addressModel) {
        return addressRepository.save(addressModel);
    }
}
