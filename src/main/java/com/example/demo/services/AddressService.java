package com.example.demo.services;

import com.example.demo.models.AddressModel;
import com.example.demo.repositories.AddressRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    public AddressModel save(AddressModel addressModel) {
        return addressRepository.save(addressModel);
    }


    public AddressModel createAddress(AddressModel addressModel) {
        return addressRepository.save(addressModel);
    }
}
