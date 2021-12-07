package com.website.demo.API.dispensary;

import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@CrossOrigin
public class DispensaryController {


    private final DispensaryService dispensaryService;

    @GetMapping("dispensaries")
    public List<Dispensary> getDispensaries(){
        return dispensaryService.getDispensaries();
    }
}
