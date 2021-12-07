package com.website.demo.API.dispensary;

import com.website.demo.API.specialization.Specialization;
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
@Entity(name = "Dispensary")
@Table(name="dispensary")
public class Dispensary {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String name;
    @ManyToMany
    @JoinTable(name = "dispensary_specialization", joinColumns = {@JoinColumn(name = "dispensary_id")}, inverseJoinColumns = {@JoinColumn(name = "specialization_id")})
    private Set<Specialization> specializations;

}
