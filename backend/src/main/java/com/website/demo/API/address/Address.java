package com.website.demo.API.address;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;

@Entity(name = "Address")
@Table(name = "address")
@Getter
@Setter
@NoArgsConstructor
public class Address {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String country;
    private String city;
    private String street;
    private Integer flatNumber;
    private String postcode;

    public Address(String country, String city, String street, Integer flat_number, String postcode) {
        this.country = country;
        this.city = city;
        this.street = street;
        this.flatNumber = flat_number;
        this.postcode = postcode;
    }
}

