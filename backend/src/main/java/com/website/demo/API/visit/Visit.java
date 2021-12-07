package com.website.demo.API.visit;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.website.demo.API.patient.Patient;
import com.website.demo.API.user.AppUser;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity(name = "Visit")
@Table(name = "visit")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
public class Visit {


    public Visit(Patient patient, AppUser doctor, LocalDateTime date) {
        this.patient = patient;
        this.doctor = doctor;
        this.date = date;
    }




    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    @DateTimeFormat(pattern = "yyyy-MM-dd hh:mm")
    private LocalDateTime date;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "app_user_id")
    private AppUser doctor;
    @ManyToOne(cascade = CascadeType.ALL)
    @JsonBackReference
    @JoinColumn(name = "patient_id")
    private Patient patient;
    private int duration;
    private Boolean finished;


    public Visit(LocalDateTime localDateTime) {
        this.date = localDateTime;
    }
}
