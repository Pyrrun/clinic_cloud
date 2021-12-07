package com.website.demo.API.user;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;

@Data
@NoArgsConstructor
public class AppUserDto {


    private String firstName;
    private String secondName;
    private String lastName;
    private String img;
    private Integer id;
    private static final String CLOUDINARY_URL = "https://res.cloudinary.com/dpkrneznq/image/upload/v1635196214/";


    public AppUserDto(String firstName, String secondName, String lastName, String img, Integer id) {
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.img = CLOUDINARY_URL + img;
        this.id = id;
    }

    public AppUserDto(AppUser appUser){
        this.firstName = appUser.getFirstName();
        this.secondName = appUser.getSecondName();
        this.lastName = appUser.getLastName();
        this.img = CLOUDINARY_URL + appUser.getImg();
        this.id = appUser.getId();
    }





//    public static AppUserDto from(DoctorDto doctorDto){
//        AppUserDto appUserDto = new AppUserDto();
//        appUserDto.firstName = doctorDto.getFirstName();
//        appUserDto.secondName = doctorDto.getSecondName();
//        appUserDto.lastName = doctorDto.getLastName();
//        appUserDto.imageName = doctorDto.getImageName();
//        appUserDto.id = doctorDto.getId();
//        return appUserDto;
//    }

}
