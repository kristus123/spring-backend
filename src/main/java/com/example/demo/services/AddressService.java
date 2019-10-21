package com.example.demo.services;

import com.example.demo.exceptions.ElementNotFoundException;
import com.example.demo.models.AddressModel;
import com.example.demo.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public Optional<AddressModel> findById(int id) {
        return addressRepository.findById(id);
    }

    public List<AddressModel> findAll() {return addressRepository.findAll();}

    public AddressModel save(AddressModel addressModel) {
        return addressRepository.save(addressModel);
    }

    public AddressModel update(Integer id, AddressModel season) throws ElementNotFoundException {
        findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find address with ID=" + id));
        return save(season);
    }

    public AddressModel deleteById(Integer id) throws ElementNotFoundException {
        AddressModel address = findById(id).orElseThrow(() -> new ElementNotFoundException("Could not find address with ID=" + id));
        addressRepository.deleteById(id);
        return address;
    }

    public AddressModel createAddress(AddressModel addressModel) {
        return addressRepository.save(addressModel);
    }
}
