package com.website.demo.API.schedule;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Getter
@Setter
public class ScheduleDto {
    private Integer id;
    private LocalDate date;
    private String startHour;
    private String endHour;

    public static ScheduleDto from(Schedule schedule){
        ScheduleDto dto = new ScheduleDto();
        dto.id = schedule.getId();
        dto.date = schedule.getDate();
        dto.startHour = schedule.getStartHour();
        dto.endHour = schedule.getEndHour();

        return dto;
    }
}
