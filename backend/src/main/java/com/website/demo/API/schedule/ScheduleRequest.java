package com.website.demo.API.schedule;


import lombok.Data;

@Data
public class ScheduleRequest {

    private String date;
    private String startHour;
    private String endHour;
    private int visitDuration;
    private int breakDuration;

}
