package com.website.demo.API.login;

import com.website.demo.cloudinary.CloudinaryConfig;
import com.website.demo.security.utils.JWTUtil;
import com.website.demo.API.user.AppUser;
import com.website.demo.API.user.AppUserRepository;
import com.website.demo.API.user.AppUserService;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.authentication.BadCredentialsException;
import org.springframework.security.authentication.DisabledException;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.stereotype.Service;



@Service
@Getter
@Setter
@AllArgsConstructor
public class LoginService {

    private final DaoAuthenticationProvider daoAuthenticationProvider;
    private final AppUserRepository appUserRepository;
    private final AppUserService appUserService;
    private final JWTUtil jwtUtil;
    private final CloudinaryConfig cloudinaryConfig;

    public LoginResponse login(String email, String password) {
        authenticate(email, password);
        AppUser appUser = (AppUser) appUserService.loadUserByUsername(email);
        String token =  jwtUtil.generateToken(appUser);
        if(appUser.getImg() != null) {
            appUser.setImg(cloudinaryConfig.getURL() + appUser.getImg());
        }
        return new LoginResponse(
                token,
                email,
                appUser.getFirstName(),
                appUser.getSecondName(),
                appUser.getLastName(),
                appUser.getId(),
                appUser.getImg(),
                appUser.getRole()
                );
    }

    private void authenticate(String email, String password) {
        try {
            daoAuthenticationProvider.authenticate(new UsernamePasswordAuthenticationToken(email, password));
        } catch (DisabledException e) {
            throw new RuntimeException("This user has not been activated");
        } catch (BadCredentialsException e) {
            throw new RuntimeException("Invalid credentials");
        }
    }
}
