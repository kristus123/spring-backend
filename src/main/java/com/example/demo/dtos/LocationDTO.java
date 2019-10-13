package com.example.demo.dtos;

import com.example.demo.models.AddressModel;
import com.example.demo.models.LocationModel;
import com.example.demo.services.AddressService;
import com.example.demo.services.LocationService;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Getter
@Setter
@NoArgsConstructor
public class LocationDTO {

    @Autowired
    LocationService locationService;
    @Autowired
    AddressService addressService;

    private AddressModel addressModel;
    private LocationModel locationModel;

    public LocationDTO(LocationModel locationModel, AddressModel addressModel) {
        addressModel.getAddresses();
        this.addressModel  = addressModel;
        this.locationModel = locationModel;



        locationModel.setAddress(addressModel);


    }
}
