package com.website.demo.API.login;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class LoginRequest {

    private String email;
    private String password;

}
