package com.website.demo.API.specialization;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class SpecializationDto {

    private String name;


    public static SpecializationDto from(Specialization specialization){
        SpecializationDto specializationDto = new SpecializationDto();
        specializationDto.name = specialization.getName();
        return specializationDto;
    }

}
