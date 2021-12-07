package com.website.demo.API.user;

import com.website.demo.API.address.Address;
import com.website.demo.API.address.AddressService;
import com.website.demo.API.registration.RegistrationRequest;
import com.website.demo.API.registration.RegistrationService;
import com.website.demo.authorities.AppUserRole;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("users")
@AllArgsConstructor
public class AppUserController {

    private final AppUserService appUserService;
    private final AddressService addressService;
    private final RegistrationService registrationService;



    @GetMapping("email")
    public void checkEmailAvailability(@RequestParam("email") String email) {
        appUserService.checkEmailAvailability(email);
    }

    @GetMapping
    public List<AppUser> list() {
        return appUserService.list();
    }



    @PostMapping
    public void addAppUser(@RequestBody RegistrationRequest request){

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
                AppUserRole.DOCTOR
        );

        appUserService.signUp(appUser);
    }



    @GetMapping("first-name")
    public String findFirstNameByEmail(@RequestParam String email){
        return appUserService.findFirstNameByEmail(email);
    }

    @GetMapping("except-for/{id}")
    public List<AppUserDto> findUsersExceptForOne(@PathVariable Integer id){
        return appUserService.findUsersExceptForOne(id);
    }





}

