package com.website.demo.API.medication;

import lombok.Data;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@Data
@CrossOrigin
public class MedicationController {

    private final MedicationService medicationService;

    @GetMapping("medications")
    public List<Medication> findMedicationsBy(@RequestParam String name){
        return medicationService.findMedicationsBy(name);
    }



}
