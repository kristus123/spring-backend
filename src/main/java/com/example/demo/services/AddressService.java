package com.example.demo.services;

import com.example.demo.models.AddressModel;
import com.example.demo.models.LocationModel;
import com.example.demo.models.PersonModel;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.repositories.LocationRepository;
import com.example.demo.repositories.PersonRepository;
import com.example.demo.repositories.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AddressService {
    @Autowired
    AddressRepository addressRepository;

    @Autowired
    PersonRepository personRepository;

    @Autowired
    LocationRepository locationRepository;

    public Optional<AddressModel> findById(int id) {
        return addressRepository.findById(id);
    }

    public List<    AddressModel> findall() {return addressRepository.findAll();}

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

            Optional<LocationModel> location = locationRepository.findByAddress(address.get());
            if (location.isPresent()) {
                location.get().setAddress(null);
                locationRepository.save(location.get());
            }



            addressRepository.delete(address.get());
            return true;
        }
        throw new RuntimeException("address Id not found");
    }

    public AddressModel createAddress(AddressModel addressModel) {
        return addressRepository.save(addressModel);
    }
}
