package com.website.demo.API.schedule;

import com.website.demo.API.user.AppUser;
import com.website.demo.API.user.AppUserRepository;
import com.website.demo.API.user.AppUserService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CrossOrigin;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

@Service
@AllArgsConstructor
@CrossOrigin
public class ScheduleService {

    private final ScheduleRepository scheduleRepository;
    private final AppUserService appUserService;


    public List<Schedule> findSchedulesForDoctorBy(String date, Integer doctorID) {
        if (date != null) {
            List<Schedule> scheduleList = new ArrayList<>();
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
            LocalDate localDate = LocalDate.parse(date, formatter);
            Integer scheduleID = scheduleRepository.findScheduleIDForDoctorByDate(localDate, doctorID);
            if(scheduleID != null){
                scheduleList.add(scheduleRepository.findById(scheduleID).get());
            }
            return scheduleList;
        }
        return scheduleRepository.findAllByDoctorID(doctorID);

    }

    public List<Schedule> getAll() {
        return scheduleRepository.findAll();
    }

    @Transactional
    public void setSchedule(String dateString,
                            String startHour, 
                            String endHour,
                            int breakDuration,
                            int visitDuration,
                            Integer doctorId) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.US);
        LocalDate localDate = LocalDate.parse(dateString, formatter);
        Schedule schedule;
        if (!findSchedulesForDoctorBy(dateString, doctorId).isEmpty()) {
            schedule = findSchedulesForDoctorBy(dateString, doctorId).get(0);
        } else {
            schedule = new Schedule();
        }
        schedule.setDate(localDate);
        schedule.setStartHour(startHour);
        schedule.setEndHour(endHour);
        schedule.setBreakDuration(breakDuration);
        schedule.setVisitDuration(visitDuration);
        scheduleRepository.save(schedule);
        appUserService.setSchedule(schedule, doctorId);


    }

    public void deleteSchedulesForDoctorBy(String date,
                                           Integer doctorId) {
        if(date == null){
            scheduleRepository.deleteById(doctorId);
            return;
        }
        Schedule schedule = findSchedulesForDoctorBy(date, appUserService.findById(doctorId).getId()).get(0);
        scheduleRepository.deleteByDateAndId(doctorId, schedule.getId());

    }
}
