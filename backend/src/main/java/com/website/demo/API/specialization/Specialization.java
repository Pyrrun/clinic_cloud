package com.website.demo.API.specialization;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.website.demo.API.dispensary.Dispensary;
import com.website.demo.API.user.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;
import java.util.Set;

@Data
@Entity(name = "Specialization")
@Table(name="specialization")
@NoArgsConstructor
public class Specialization {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany(mappedBy = "specializations")
    @JsonBackReference
    private Set<Dispensary> dispensaries;

    public Specialization(String name){
        this.name = name;
    }

}
