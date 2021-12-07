

package com.website.demo.API.visit;

import com.website.demo.API.patient.PatientDto;
import com.website.demo.API.user.doctor.DoctorDto;
import com.website.demo.API.visit.response.VisitResponse;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class VisitDto extends VisitResponse {

    public LocalDateTime date;
    private DoctorDto doctor;
    private PatientDto patient;
    private int duration;
    private Boolean finished;

    public static VisitDto from(Visit visit){
        VisitDto visitDto = new VisitDto();
        visitDto.date = visit.getDate();
        visitDto.patient = new PatientDto(visit.getPatient());
     //   visitDto.patient = new PatientDto(visit.getPatient());
        visitDto.doctor = DoctorDto.from(visit.getDoctor());
        visitDto.duration = visit.getDuration();
        visitDto.finished = visit.getFinished();
        return visitDto;
    }




}
