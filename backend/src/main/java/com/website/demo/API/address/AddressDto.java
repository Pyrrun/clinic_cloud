package com.website.demo.API.address;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class AddressDto {

    private Integer id;
    private String country;
    private String city;
    private String street;
    private Integer streetNumber;
    private Integer flatNumber;
    private String postcode;

    public static AddressDto from(Address address) {
        AddressDto dto = new AddressDto();

        dto.id = address.getId();
        dto.country = address.getCountry();
        dto.city = address.getCity();
        dto.street = address.getStreet();
        dto.flatNumber = address.getFlatNumber();
        dto.postcode = address.getPostcode();

        return dto;
    }
}
