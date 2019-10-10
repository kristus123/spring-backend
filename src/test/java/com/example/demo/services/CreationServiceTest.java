package com.example.demo.services;

import com.example.demo.dtos.LocationDTO;
import com.example.demo.models.AddressModel;
import com.example.demo.models.LocationModel;
import com.example.demo.repositories.AddressRepository;
import com.example.demo.repositories.LocationRepository;
import org.junit.Before;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collector;
import java.util.stream.Collectors;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CreationServiceTest {

    @Autowired
    CreationService creationService;

    @Autowired
    AddressRepository addressRepository;

    @Autowired LocationRepository locationRepository;


    @Test
    void createAddressAndLocations() {

        //
        //      CLEAN OPP DISSE TESTENE
        //

        creationService.createAddress(new AddressModel("250", "bergen", "NORGE", "vestre Vardane 30"));

        assertTrue(addressRepository.findByAddresses("vestre Vardane 30").isPresent());

        AddressModel addressModel = addressRepository.findByAddresses("vestre Vardane 30").get();


        LocationModel locationModel = creationService.createLocation(addressModel, "billy-town", "a nice palce yeehaw");

        assertTrue(locationRepository.findByName("billy-town").isPresent());


        LocationModel loc = creationService.createLocation(new LocationDTO(locationModel, addressModel));
        assertTrue(loc.getAddress().getCountry().equals("NORGE"));

        List<LocationModel> models =  locationRepository.findAll();

        assertFalse(models.isEmpty());

        assertFalse(models.stream()
                .filter(m -> m.getName().equals("billy-town"))
                .collect(Collectors.toList())
                .isEmpty()
        );

    }


    @Test
    void testMappingLocationToAddress() {
        AddressModel addressModel = new AddressModel("5305", "OSLO", "NOREG", "vestre vardane 30");
        LocationModel locationModel = new LocationModel(addressModel, "brann stadium", "en brennende fin by");
        creationService.createLocation(new LocationDTO(locationModel, addressModel));

        Optional<LocationModel>  location = locationRepository.findByName("brann stadium");
        assertTrue(location.isPresent());

        assertTrue(location.get().getAddress().getCountry().equals("NOREG"));



    }

    @Test
    void assignAddressToLocationTest() {
        LocationModel locationModel = creationService.createLocation(
                new AddressModel("5555", "Oslo", "Norge", "Vestre 30"),
                "Bislett", "et fint sted hvor Kristian bor");

        addressRepository.save(new AddressModel("5555", "Trondheim", "Norway"));

        creationService.createAddress("5306", "Trondheim", "Norway", "Trondo 30");

        Optional<AddressModel>  addressModel = addressRepository.findByAddresses("Trondo 30");
        assertTrue(addressModel.isPresent());




        locationModel = locationRepository.findByName("Bislett").get();


        creationService.assignAddressToLocation(locationModel, addressModel.get().getAddressId());

        locationModel = locationRepository.findByName("Bislett").get();

        assertNotNull(locationModel.getAddress());

        assertEquals(locationModel.getAddress().getCity(), "Trondheim");


    }


}