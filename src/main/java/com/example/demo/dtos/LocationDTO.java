package com.example.demo.dtos;

import com.example.demo.models.AddressModel;
import com.example.demo.models.LocationModel;
import com.example.demo.services.AddressService;
import com.example.demo.services.LocationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

@Getter
@Setter
@NoArgsConstructor
public class LocationDTO {

    private Integer addressId;
    private String name;
    private String description;

    public LocationDTO(Integer addressId, String name) {
        this.addressId = addressId;
        this.name = name;
    }

}
