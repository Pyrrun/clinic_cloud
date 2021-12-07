package com.website.demo.API.prescription;

import com.website.demo.API.medication.Medication;
import com.website.demo.API.schedule.Schedule;
import com.website.demo.API.visit.Visit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Table(name = "prescription")
@Entity(name = "Prescription")
@Data
@NoArgsConstructor
public class Prescription {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String code;
    @ManyToOne
    @JoinColumn(name = "visit_id")
    private Visit visit;
    @ManyToMany
    @JoinTable(name = "prescription_medication", joinColumns = {@JoinColumn(name = "prescription_id")}, inverseJoinColumns = {@JoinColumn(name = "medication_id")})
    private Set<Medication> medications;

    public Prescription(String code, Visit visit){
        this.code = code;
        this.visit = visit;
    }
}
