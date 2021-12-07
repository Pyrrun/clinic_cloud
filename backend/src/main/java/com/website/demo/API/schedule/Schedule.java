package com.website.demo.API.schedule;

import com.fasterxml.jackson.annotation.JsonBackReference;

import com.website.demo.API.user.AppUser;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Set;

@Entity(name = "Schedule")
@Table(name = "schedule")
@Getter
@Setter
@NoArgsConstructor
public class Schedule {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private LocalDate date;
    private String startHour;
    private String endHour;
    @ManyToMany(mappedBy = "schedules")
    @JsonBackReference
    private Set<AppUser> doctors;
    private int breakDuration;
    private int visitDuration;


    public Schedule(LocalDate date, String startHour, String endHour, int breakDuration, int visitDuration) {
        this.date = date;
        this.startHour = startHour;
        this.endHour = endHour;
        this.breakDuration = breakDuration;
        this.visitDuration = visitDuration;
    }
}
