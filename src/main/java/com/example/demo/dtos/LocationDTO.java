package com.example.demo.dtos;

import com.example.demo.models.AddressModel;
import com.example.demo.models.LocationModel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class LocationDTO {
    private AddressModel addressModel;
    private LocationModel locationModel;

    public LocationDTO(LocationModel locationModel, AddressModel addressModel) {
        locationModel.setAddress(addressModel);

        this.addressModel  = addressModel;
        this.locationModel = locationModel;
    }
}
