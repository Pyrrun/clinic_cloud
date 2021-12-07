package com.website.demo.API.address;

import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.data.domain.Example;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.when;

class AddressServiceTest {

    @Test
    void testGettingAddressAndSavingIfNotFound() {

        AddressRepository addressRepository = Mockito.mock(AddressRepository.class);
        AddressService addressService = new AddressService(addressRepository);
        Address address = new Address("Polska", "Warszawa", "Okońska", 1, "02-032");
        when(addressRepository.exists(Example.of(address))).thenReturn(true);
        when(addressRepository.findOne(Example.of(address)).orElseThrow(IllegalStateException::new))
                .thenReturn(address);
        when(addressRepository.save(address)).thenReturn(address);
        assertThat(addressService.getAddressAndSaveIfNotExists(address))
                .isNotNull();

    }
}