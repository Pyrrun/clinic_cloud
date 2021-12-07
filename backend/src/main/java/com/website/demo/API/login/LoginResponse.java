package com.website.demo.API.login;


import com.website.demo.authorities.AppUserRole;
import com.website.demo.cloudinary.CloudinaryConfig;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;


@Getter
@Setter
public class LoginResponse {


    private String token;
    private String email;
    private String firstName;
    private String secondName;
    private String lastName;
    private Integer id;
    private String img;
    private AppUserRole role;

    public LoginResponse(String token,
                         String email,
                         String firstName,
                         String secondName,
                         String lastName,
                         Integer id,
                         String img,
                         AppUserRole role) {
        this.token = token;
        this.email = email;
        this.firstName = firstName;
        this.secondName = secondName;
        this.lastName = lastName;
        this.id = id;
        this.img = img;
        this.role = role;
    }
}
