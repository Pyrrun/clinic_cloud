package com.website.demo.API.user;

import com.website.demo.API.person.PersonRepository;
import com.website.demo.API.registration.token.ConfirmationToken;
import com.website.demo.API.registration.token.ConfirmationTokenService;
import com.website.demo.API.schedule.Schedule;
import lombok.AllArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.UUID;


@Service
@AllArgsConstructor
public class AppUserService implements UserDetailsService {



    private final static String USER_NOT_FOUND_MSG = "User with email %s not found";
    private final static String EMAIL_TAKEN_MSG = "E-mail %s is already in use";
    private final AppUserRepository appUserRepository;
    private final PersonRepository personRepository;
    private final BCryptPasswordEncoder bCryptPasswordEncoder;
    private final ConfirmationTokenService confirmationTokenService;




    @Override
    public UserDetails loadUserByUsername(String email) throws UsernameNotFoundException {
        return appUserRepository.findByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }

    public String findFirstNameByEmail(String email){
        return appUserRepository.findFirstNameByEmail(email).orElseThrow(() -> new UsernameNotFoundException(String.format(USER_NOT_FOUND_MSG, email)));
    }



    public String signUp(AppUser appUser) {
        boolean userExists = appUserRepository.findByEmail(appUser.getEmail()).isPresent();
        if (userExists) throw new IllegalStateException(String.format(EMAIL_TAKEN_MSG, appUser.getEmail()));
        String encodedPassword = bCryptPasswordEncoder.encode(appUser.getPassword());
        appUser.setPassword(encodedPassword);
        personRepository.save(appUser.getPerson());
        AppUser savedUser = appUserRepository.save(appUser);
        String token = UUID.randomUUID().toString();
        ConfirmationToken confirmationToken = new ConfirmationToken(
                token,
                LocalDateTime.now(),
                LocalDateTime.now().plusMinutes(10),
                savedUser
        );
        confirmationTokenService.saveConfirmationToken(confirmationToken);
        return token;
    }

    public void enableAppUser(String email) {
         appUserRepository.enableAppUser(email);
    }

    public void checkEmailAvailability(String email) {
        if (appUserRepository.findByEmail(email).isPresent()) throw new IllegalStateException("E-mail already exists");
    }

    public void setImg(String img, String email){
        appUserRepository.setImg(img, email);
    }

    public List<AppUser> list() {
        return appUserRepository.findAll();
    }



    public void setSchedule(Schedule schedule, Integer id) {
        AppUser appUser = findById(id);
        if(!appUser.getSchedules().contains(schedule)){
            appUserRepository.setSchedule(schedule.getId(), id);
        }
    }

    public AppUser findById(Integer id){
        return appUserRepository.findById(id).orElseThrow(() -> new NoSuchElementException("User with id: " + id + "not found"));
    }

    public List<AppUserDto> findUsersExceptForOne(Integer id) {
        return appUserRepository.findAllExceptFor(id);
    }


}
