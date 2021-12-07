package com.website.demo.API.user.receptionist;

import com.website.demo.API.user.AppUserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

@Service
@Data
public class ReceptionistService {

    private final AppUserRepository appUserRepository;

}
