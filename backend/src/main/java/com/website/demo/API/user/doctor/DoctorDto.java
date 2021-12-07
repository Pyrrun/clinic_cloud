package com.website.demo.API.user.doctor;

import com.website.demo.API.person.Person;
import com.website.demo.API.person.PersonDto;
import com.website.demo.API.schedule.Schedule;
import com.website.demo.API.specialization.Specialization;
import com.website.demo.API.specialization.SpecializationDto;
import com.website.demo.API.user.AppUser;
import com.website.demo.cloudinary.CloudinaryConfig;
import lombok.Data;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Set;


@Data
@NoArgsConstructor
public class DoctorDto {

    private Integer id;
    private String firstName;
    private String secondName;
    private String lastName;
    private String email;
    private String phone;
    private String img;
    private Set<SpecializationDto> specializations;
    private static final String CLOUDINARY_URL = "https://res.cloudinary.com/dpkrneznq/image/upload/v1635196214/";

    public DoctorDto(
            String firstName,
            String secondName,
            String lastName
    ){
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
    }



    public DoctorDto(AppUser appUser, Person person){
        id = appUser.getId();
        firstName = person.getFirstName();
        secondName = person.getSecondName();
        lastName = person.getLastName();
        email = person.getEmail();
        phone = person.getPhone();
        img = CLOUDINARY_URL + appUser.getImg();
    }


    public static DoctorDto from(AppUser appUser) {
        DoctorDto dto = new DoctorDto();
        dto.id = appUser.getId();
        dto.img =  CLOUDINARY_URL + appUser.getImg();
        dto.firstName = appUser.getPerson().getFirstName();
        dto.secondName = appUser.getPerson().getSecondName();
        dto.lastName = appUser.getPerson().getLastName();
        dto.email = appUser.getPerson().getEmail();
        dto.phone = appUser.getPerson().getPhone();

        return dto;
    }


}
