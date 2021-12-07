package com.website.demo.API.address;

import lombok.Data;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class AddressService {


    private final AddressRepository addressRepository;



    public Address getAddressAndSaveIfNotExists(Address address){
        boolean addressExists = addressRepository.exists(Example.of(address));
        if (addressExists) return addressRepository.findOne(Example.of(address)).orElseThrow(IllegalStateException::new);
        addressRepository.save(address);
        return address;
    }

    public List<Address> getAll() {
        return addressRepository.findAll();
    }
}
