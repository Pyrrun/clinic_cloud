package com.website.demo.API.person;

import com.website.demo.API.address.Address;
import com.website.demo.API.patient.Patient;
import com.website.demo.API.user.AppUser;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Table(name="person")
@NoArgsConstructor
@Getter
@Setter
public class Person {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String firstName;
    private String secondName;
    private String lastName;
    private LocalDate birthdate;
    private String phone;
    private String email;
    @ManyToOne(optional = false)
    @JoinColumn(name = "address_id")
    private Address address;

    public Person(String firstName,
                  String secondName,
                  String lastName,
                  LocalDate birthdate,
                  String phone,
                  String email,
                  Address address) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public Person(String firstName,
                  String secondName,
                  String lastName,
                  String email,
                  String phone,
                  Address address,
                  LocalDate birthdate) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.birthdate = birthdate;
        this.phone = phone;
        this.email = email;
        this.address = address;
    }

    public void setAddress(Address address){
        this.address = address;
    }





}
