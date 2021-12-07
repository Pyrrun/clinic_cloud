package com.website.demo.API.registration;

import lombok.*;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EqualsAndHashCode
@ToString
public class ConfirmationRequest {
    private String email;
    private String firstName;
}
