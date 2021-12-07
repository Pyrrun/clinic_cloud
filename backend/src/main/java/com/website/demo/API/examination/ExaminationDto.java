package com.website.demo.API.examination;

import lombok.Data;

@Data
public class ExaminationDto {

    private String interview;
    private String research;

    public static ExaminationDto from(Examination examination){
        ExaminationDto examinationDto = new ExaminationDto();
        examinationDto.interview = examination.getInterview();
        examinationDto.research = examination.getResearch();
        return examinationDto;
    }

}
