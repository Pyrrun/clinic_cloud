package com.website.demo.API.login;

import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@CrossOrigin
@RestController
@AllArgsConstructor
@RequestMapping("login")
public class LoginController {

    private final LoginService loginService;

    @PostMapping
    @ResponseBody
    public LoginResponse login(@RequestBody LoginRequest loginRequest) {
        return loginService.login(loginRequest.getEmail(), loginRequest.getPassword());
    }

}
