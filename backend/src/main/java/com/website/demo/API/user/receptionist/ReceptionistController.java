package com.website.demo.API.user.receptionist;

import lombok.Data;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Data
public class ReceptionistController {

    private final ReceptionistService receptionistService;

}
