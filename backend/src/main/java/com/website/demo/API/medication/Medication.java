package com.website.demo.API.medication;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.website.demo.API.prescription.Prescription;
import com.website.demo.API.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Set;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Table(name = "medication")
@Entity(name="Medication")
public class Medication {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    private int onPrescription;
    private String description;
    @ManyToMany(mappedBy = "medications")
    @JsonBackReference
    private Set<Prescription> prescriptions;
    private String imageName;

}
