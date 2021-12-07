package com.website.demo.API.patient;

import com.website.demo.API.address.Address;
import com.website.demo.API.address.AddressDto;
import com.website.demo.API.person.Person;
import com.website.demo.API.user.AppUser;
import com.website.demo.API.visit.Visit;
import com.website.demo.API.visit.VisitDto;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.time.LocalDate;

@Data
public class PatientDto {

    private Integer id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String pesel;
    private LocalDate birthdate;
    private String phone;
    private Address address;
    private String email;
    private Integer doctorId;
   // private Set<VisitDto> visit;

    public PatientDto(Integer id,
                      String firstName,
                      String secondName,
                      String lastName,
                      String pesel,
                      LocalDate birthdate,
                      String phone,
                      Address address,
                      String email,
                      Integer doctorId) {
        this.id = id;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.pesel = pesel;
        this.birthdate = birthdate;
        this.phone = phone;
        this.address = address;
        this.email = email;
        this.doctorId = doctorId;
    }

//    public PatientDto(Patient patient) {
//        this.id = id;
//        this.firstName = firstName;
//        this.secondName = secondName;
//        this.lastName = lastName;
//        this.pesel = pesel;
//        this.birthdate = birthdate;
//        this.phone = phone;
//        this.address = address;
//        this.email = email;
//        this.doctorId = doctorId;
//    }


    public PatientDto(Patient patient){
        this.id = patient.getId();
        this.firstName = patient.getFirstName();
        this.secondName = patient.getSecondName();
        this.lastName = patient.getLastName();
        this.pesel = patient.getPesel();
        this.birthdate = patient.getBirthdate();
        this.phone = patient.getPhone();
        this.address = patient.getAddress();
        this.email = patient.getEmail();
        this.doctorId = patient.getAppUser().getId();

    }

}
