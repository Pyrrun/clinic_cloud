package com.website.demo.API.specialization;


import lombok.Getter;
import lombok.Setter;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Getter
@Setter
@CrossOrigin
public class SpecializationController {

    private final SpecializationService specializationService;


    public SpecializationController(SpecializationService specializationService) {
        this.specializationService = specializationService;
    }


    @GetMapping("specializations")
    public List<Specialization> getAll(){
        return specializationService.getAll();
    }

    @GetMapping("dispensaries/{id}/specializations")
    public List<Specialization> getSpecializationsForDispensary(@PathVariable Integer id){
        return specializationService.getSpecializationsForDispensary(id);
    }

}
