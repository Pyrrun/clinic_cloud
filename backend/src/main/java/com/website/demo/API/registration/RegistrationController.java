package com.website.demo.API.registration;

import com.website.demo.API.address.Address;
import com.website.demo.API.address.AddressService;
import com.website.demo.authorities.AppUserRole;
import com.website.demo.API.registration.ConfirmationRequest;
import com.website.demo.API.registration.RegistrationRequest;
import com.website.demo.API.registration.RegistrationService;
import com.website.demo.API.registration.TokenRequest;
import com.website.demo.API.registration.token.ConfirmationTokenService;
import com.website.demo.API.specialization.SpecializationService;
import com.website.demo.API.user.AppUser;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@Data
@CrossOrigin
@RequestMapping("registration")
public class RegistrationController {

    private final RegistrationService registrationService;
    private final AddressService addressService;
    private final ConfirmationTokenService confirmationTokenService;
    private final SpecializationService specializationService;

    @PostMapping
    public void register(@ModelAttribute @Valid RegistrationRequest request){
        AppUserRole role;
        System.out.println(request);
        if(request.getRegistrationType().equals("doctorType")){
            role = AppUserRole.DOCTOR;
        }
        else{
            role = AppUserRole.RECEPTION_WORKER;
        }

        Address requestAddress = new Address(
                request.getCountry(),
                request.getCity(),
                request.getStreet(),
                request.getFlatNumber(),
                request.getPostcode()
        );

        AppUser appUser =  new AppUser(
                request.getFirstName(),
                request.getSecondName(),
                request.getLastName(),
                request.getEmail(),
                request.getPassword(),
                request.getPhone(),
                requestAddress,
                registrationService.birthdayToLocalDate(request.getBirthdate()),
                role
        );
        registrationService.register(appUser, request.getImg());
        if(role == AppUserRole.DOCTOR){
            specializationService.setSpecializationsForDoctor(appUser, request.getSpecializationNames());
        }
    }


    @GetMapping("confirm")
    public String confirm(@RequestParam("token") String token){
        return registrationService.confirmToken(token);
    }



    @PostMapping("resend-email")
    public void resendEmail(@RequestBody ConfirmationRequest confirmationRequest){
        registrationService.resendEmail(confirmationRequest.getEmail(), confirmationRequest.getFirstName());
    }


    @PostMapping("resend-token")
    public void resendEmailToken(@RequestBody TokenRequest tokenRequest){
        AppUser appUser = confirmationTokenService.getUserByToken(tokenRequest.getToken());
        registrationService.resendEmail(appUser.getEmail(), appUser.getFirstName());
    }



}
