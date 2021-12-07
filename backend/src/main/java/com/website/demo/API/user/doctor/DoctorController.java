package com.website.demo.API.user.doctor;

import com.website.demo.API.user.AppUserDto;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@CrossOrigin
public class DoctorController {

    private final DoctorService doctorService;

    @GetMapping("doctors")
    public List<DoctorDto> getDoctorsBy(@RequestParam(required = false) String name, @RequestParam(required = false) String date) {
        return doctorService.getDoctorsBy(date, name);
    }

    @GetMapping("doctors/{id}/name")
    public String getNameById(@PathVariable Integer id){
        return doctorService.getNameById(id);
    }

}
