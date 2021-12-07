package com.website.demo.API.registration.token;

import com.website.demo.API.user.AppUser;
import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Getter
@Setter
@RequestMapping("tokens")
public class ConfirmationTokenController {


    private final ConfirmationTokenService confirmationTokenService;


    public ConfirmationTokenController(ConfirmationTokenService confirmationTokenService) {
        this.confirmationTokenService = confirmationTokenService;
    }

    @GetMapping
    public List<ConfirmationToken> getAll(){
        return confirmationTokenService.getAll();
    }

   @GetMapping("getUser")
    public AppUser getUserByToken(@RequestParam String token){
        return confirmationTokenService.getUserByToken(token);
   }

}
