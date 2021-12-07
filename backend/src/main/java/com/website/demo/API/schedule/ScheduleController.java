package com.website.demo.API.schedule;

import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@CrossOrigin
public class ScheduleController {

    private final ScheduleService scheduleService;

    public ScheduleController(ScheduleService scheduleService) {
        this.scheduleService = scheduleService;
    }

    @GetMapping
    public List<ScheduleDto> getAll(){
        return scheduleService.getAll()
                .stream()
                .map(ScheduleDto::from)
                .collect(Collectors.toList());
    }



    @GetMapping("doctors/{id}/schedules")
    public List<Schedule> findSchedulesForDoctorBy(@RequestParam(required = false)String date, @PathVariable Integer id){
        return scheduleService.findSchedulesForDoctorBy(date, id);
    }

    @PostMapping("doctors/{doctorId}/schedules")
    public void setSchedule(@RequestBody ScheduleRequest scheduleRequest, @PathVariable Integer doctorId){
        scheduleService.setSchedule(
                scheduleRequest.getDate(),
                scheduleRequest.getStartHour(),
                scheduleRequest.getEndHour(),
                scheduleRequest.getBreakDuration(),
                scheduleRequest.getVisitDuration(),
                doctorId
                );
    }

    @DeleteMapping("doctors/{id}/schedules")
    public void deleteSchedulesForDoctorBy(@RequestParam String date, @PathVariable Integer id){
        scheduleService.deleteSchedulesForDoctorBy(date, id);
    }




}
