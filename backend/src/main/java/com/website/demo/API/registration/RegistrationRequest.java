package com.website.demo.API.registration;


import com.website.demo.authorities.AppUserRole;
import com.website.demo.API.specialization.SpecializationNames;
import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.Pattern;

@Getter
@AllArgsConstructor
@EqualsAndHashCode
@ToString
public class RegistrationRequest {


    @Pattern(regexp="^[A-Za-z\\p{L}]+$")
    private final String firstName;
    @Pattern(regexp="^[A-Za-z\\p{L}]*$")
    private final String secondName;
    @Pattern(regexp="^[A-Za-z\\p{L}]+$")
    private final String lastName;
    @Pattern(regexp="^[0-9]*$")
    private final String phone;
    @Pattern(regexp= "^(?=.*?[A-Z])(?=.*?[a-z])(?=.*?[0-9])(?=.*?[#?!@$%^&*-]).{8,}$")
    private final String password;
    @Pattern(regexp="[a-z0-9!#$%&'*+/=?^_`{|}~-]+(?:.[a-z0-9!#$%&'*+/=?^_`{|}~-]+)*@(?:[a-z0-9](?:[a-z0-9-]*[a-z0-9])?.)+[a-z0-9](?:[a-z0-9-]*[a-z0-9])?")
    private final String email;
    @Pattern(regexp="^[A-Za-z\\p{L}]+$")
    private final String country;
    @Pattern(regexp="^[A-Za-z\\p{L}]+$")
    private final String city;
    @Pattern(regexp="^[a-zA-Z\\p{L}]+( [0-9]+[a-z]?)?")
    private final String street;
    private final Integer flatNumber;
    @Pattern(regexp="^([0-9]{2})(-[0-9]{3})?$")
    private final String postcode;
    @Pattern(regexp = "^\\d{4}\\-(0[1-9]|1[012])\\-(0[1-9]|[12][0-9]|3[01])$")
    private final String birthdate;
    private final String registrationType;
    private final String specializationNames;
    private final MultipartFile img;
    private AppUserRole role;

    public void setRole(AppUserRole role) {
        this.role = role;
    }
}
