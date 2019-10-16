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

    @Autowired
    LocationService locationService;
    @Autowired
    AddressService addressService;

    private AddressModel addressModel;
    private LocationModel locationModel;

    private Optional<Integer> addressId;

    public LocationDTO(LocationModel locationModel, AddressModel addressModel) {
        System.out.println("consturctor 1 blir brukt");
        addressModel.getAddresses();
        this.addressModel  = addressModel;
        this.locationModel = locationModel;



        locationModel.setAddress(addressModel);


    }

    public LocationDTO(LocationModel locationModel, int addressId) {
        System.out.println("INNI RETT KONSTURTURO BABY");
        Optional<AddressModel> address = addressService.findById(addressId);
        if (address.isPresent()) {
            System.out.println("ADDRESS ISS PRESENT BABYYYY");

            this.addressModel  = address.get();
            this.locationModel = locationModel;

            locationModel.setAddress(addressModel);

        }
    }
}
