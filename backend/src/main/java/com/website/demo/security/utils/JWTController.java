package com.website.demo.security.utils;


import lombok.Data;
import org.springframework.web.bind.annotation.*;

@RestController
@Data
@RequestMapping("jwt")
public class JWTController {

    private final JWTUtil jwtUtil;


    @CrossOrigin
    @GetMapping
    public void validate(){

    }


}
