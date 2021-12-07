package com.website.demo.API.person;

import com.website.demo.API.address.AddressDto;
import com.website.demo.API.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
public class PersonDto {

    private String firstName;
    private String secondName;
    private String lastName;
    private LocalDate birthdate;
    private String phone;
    private String email;


    private AddressDto addressDto;

    public PersonDto(Person person) {
        this.firstName = person.getFirstName();
        this.secondName = person.getSecondName();
        this.lastName = person.getLastName();
        this.birthdate = person.getBirthdate();
        this.phone = person.getPhone();
        this.email = person.getEmail();
        //  this.addressDto = person.ge

    }


    public static PersonDto from(Person person) {
        PersonDto personDto = new PersonDto();
        personDto.firstName = person.getFirstName();
        personDto.secondName = person.getSecondName();
        personDto.lastName = person.getLastName();
        personDto.birthdate = person.getBirthdate();
        personDto.phone = person.getPhone();
        personDto.email = person.getEmail();
        personDto.addressDto = AddressDto.from(person.getAddress());
        return personDto;
    }

}
