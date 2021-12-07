package com.website.demo.API.address;

import lombok.Data;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/address")
@Data
public class AddressController {

    private final AddressService addressService;


    @GetMapping
    public List<AddressDto> getAll() {
        return addressService.getAll()
                .stream()
                .map(AddressDto::from)
                .collect(Collectors.toList());
    }
}
