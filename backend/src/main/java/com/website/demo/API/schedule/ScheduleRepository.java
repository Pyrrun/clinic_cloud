package com.website.demo.API.schedule;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface ScheduleRepository extends JpaRepository<Schedule, Integer> {

    @Query(value = "select schedule_id from doctor_schedule where schedule_id in (\n" +
            "    select id from schedule where date = ?1\n" +
            ") and app_user_id = ?2", nativeQuery = true)
    Integer findScheduleIDForDoctorByDate(LocalDate date, Integer doctor_id);


    @Query(value = "select * from schedule where id in(\n" +
            "    select schedule_id from doctor_schedule where app_user_id=?1\n" +
            ")", nativeQuery = true)
    List<Schedule> findAllByDoctorID(Integer doctorId);

    @Transactional
    @Modifying
    @Query(value = "delete from doctor_schedule where app_user_id = ?1 and schedule_id = ?2 ", nativeQuery = true)
    void deleteByDateAndId(Integer doctorId, Integer scheduleId);
}
