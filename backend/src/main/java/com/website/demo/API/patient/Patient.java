package com.website.demo.API.patient;

import com.website.demo.API.address.Address;
import com.website.demo.API.person.Person;
import com.website.demo.API.user.AppUser;
import com.website.demo.API.visit.Visit;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Table(name = "patient")
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Patient {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @OneToMany(mappedBy = "patient", cascade = CascadeType.ALL)
    private Set<Visit> visitSet;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private AppUser appUser;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "person_id")
    private Person person;
    private String pesel;

    public String getFirstName() {
        return person.getFirstName();
    }

    public String getSecondName() {
        return person.getSecondName();
    }

    public String getLastName() {
        return person.getLastName();
    }

    public LocalDate getBirthdate() {
        return person.getBirthdate();
    }

    public String getPhone() {
        return person.getPhone();
    }

    public String getEmail() {
        return person.getEmail();
    }

    public Address getAddress() {
        return person.getAddress();
    }

    public AppUser getDoctor(){
        return getAppUser();
    }

//    public Patient(String firstName, String lastName, String secondName) {
//        this.firstName = firstName;
//        this.lastName = lastName;
//        this.secondName = secondName;
//    }

    public Patient(String firstName,
                   String secondName,
                   String lastName,
                   String pesel,
                   LocalDate birthdate,
                   String phone,
                   String email,
                   Address address,
                   AppUser appUser
    ) {
        person = new Person(
                firstName,
                secondName,
                lastName,
                email,
                phone,
                address,
                birthdate
        );
        this.pesel = pesel;
        this.appUser = appUser;
    }

}
