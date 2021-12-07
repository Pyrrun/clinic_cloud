package com.website.demo.API.user.doctor;

import com.website.demo.API.specialization.SpecializationDto;
import com.website.demo.API.user.AppUser;
import com.website.demo.API.user.AppUserRepository;
import com.website.demo.API.user.AppUserDto;
import com.website.demo.API.visit.VisitService;
import com.website.demo.cloudinary.CloudinaryConfig;

import lombok.Data;
import org.apache.tomcat.jni.Local;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
@Data
public class DoctorService {

    private final AppUserRepository appUserRepository;
    private final VisitService visitService;





    public List<DoctorDto> getDoctorsBy(String dateString, String name){
        List<DoctorDto> doctors;
        if(dateString == null && name == null){
            doctors = appUserRepository.findAllDoctors();
        }
        else if(dateString == null){
            doctors = appUserRepository.findByFirstNameStartsWith(name);
        }
        else {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
            LocalDate date = LocalDate.parse(dateString, formatter);
            doctors = appUserRepository.findAllByScheduleDate(date);
        }
        for(DoctorDto doctor : doctors){

            Set<SpecializationDto> specializationDtoSet = appUserRepository.getSpecializationsForDoctor(doctor.getId())
                    .stream()
                    .map(SpecializationDto::from)
                    .collect(Collectors.toSet());
            doctor.setSpecializations(specializationDtoSet);
        }
        return doctors;
    }

    public String getNameById(Integer id) {
        DoctorName doctorName = appUserRepository.getDoctorName(id);
        return doctorName.getFirstName() + " " + doctorName.getSecondName() + " " + doctorName.getLastName();
    }

//    public List<AppUserDto> getDoctorsExceptForOne(Integer id){
//        return appUserRepository.findAllExceptFor(id)
//                .stream()
//                .map(AppUserDto::from)
//                .collect(Collectors.toList());
//    }

}
