package com.example.demo.services;

import com.example.demo.models.AddressModel;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureWebMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@AutoConfigureWebMvc
@TestInstance(TestInstance.Lifecycle.PER_CLASS)
class AddressServiceTest {

    @Autowired
    AddressService addressService;

    private static int addressId;

    @BeforeAll
    void createData() {
        addressId = addressService.createAddress(new AddressModel("54034", "asdasd", "NORAGA", "VEAJKS 890")).getAddressId();
    }


    @Test
    void findById() {
        Optional<AddressModel> address = addressService.findById(addressId);
        assertTrue(address.isPresent());

        assertEquals(address.get().getCity(), "asdasd");
        assertEquals(address.get().getCountry(), "NORAGA");
        assertEquals(address.get().getPostalCode(), "54034");
        assertEquals(address.get().getAddresses()[0], "VEAJKS 890");
    }

    @Test
    void findall() {
        assertFalse(addressService.findall().isEmpty());
    }

    @Test
    void save() {
        AddressModel address =addressService.findById(addressId).get();
        String country = address.getCountry();

        address.setCountry("SWEDEN");

        addressService.save(address);

        assertNotEquals(country, address.getCountry());

        address = addressService.findById(addressId).get();

        assertEquals(address.getCountry(), "SWEDEN");

    }

    @Test
    void createAddress() {
        AddressModel address = addressService.createAddress(new AddressModel("V30", "V30", "V30", "V30"));
        AddressModel queryAddress = addressService.findById(address.getAddressId()).get();

        //assertEquals(address.getAddressId(), queryAddress.getAddressId());

    }

}