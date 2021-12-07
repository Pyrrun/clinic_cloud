package com.website.demo.API.registration.token;

import com.website.demo.API.user.AppUser;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;

@Service
@AllArgsConstructor
public class ConfirmationTokenService {

    private final ConfirmationTokenRepository confirmationTokenRepository;

    public void saveConfirmationToken(ConfirmationToken token){
        confirmationTokenRepository.save(token);
    }

    public Optional<ConfirmationToken> getToken(String token){
        return confirmationTokenRepository.findByToken(token);
    }

    public int confirmToken(String token){
        return confirmationTokenRepository.updateConfirmedAt(token, LocalDateTime.now());
    }

    public void removeToken(ConfirmationToken token){
        confirmationTokenRepository.delete(token);
    }

    public List<ConfirmationToken> getAll(){
        return confirmationTokenRepository.findAll();
    }


    public AppUser getUserByToken(String token) {
        return confirmationTokenRepository
                .findByToken(token)
                .orElseThrow(() -> new NoSuchElementException("Token with value: " + token + " not found."))
                .getAppUser();
    }
}
