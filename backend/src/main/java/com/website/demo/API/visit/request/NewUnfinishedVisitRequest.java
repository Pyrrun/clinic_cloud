package com.website.demo.API.visit.request;

import lombok.Data;

@Data
public class NewUnfinishedVisitRequest {

    private Integer patientId;
    private Integer doctorId;
    private String date;
    private int duration;

}
