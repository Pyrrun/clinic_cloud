package com.website.demo.API.visit;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Repository
public interface VisitRepository extends JpaRepository<Visit, Integer> {

    @Query(value = "select * from visit where date(date)= ?1 and app_user_id = ?2 and finished = ?3", nativeQuery = true)
    List<Visit> findAllVisitsForOneDoctorByDateAndFinished(LocalDate date, Integer id, Boolean finished);

    @Query(value = "select * from visit where date(date) = ?1 and app_user_id = ?2", nativeQuery = true)
    List<Visit> findAllVisitsForOneDoctorByDate(LocalDate date, Integer id);

    List<Visit> findByPatient_id(Integer patientID);

    List<Visit> findByPatient_idAndDoctor_IdAndFinished(Integer patientID, Integer doctorID, Boolean finished);

    List<Visit> findByPatient_idAndDoctor_Id(Integer patientID, Integer doctorID);


    List<Visit> findByDoctor_Id(Integer doctorId);

    List<Visit> findByPatient_idAndFinished(Integer patientID, Boolean finished);

    @Transactional
    @Modifying
    @Query(value = "insert into visit(date, finished, duration, app_user_id, patient_id) values (?1, false, ?2, ?3, ?4)", nativeQuery = true)
    void saveNewVisit(LocalDateTime date, int duration, Integer doctorId, Integer patientId);

    @Query(value = "select * from visit where patient_id=?1 and app_user_id=?2 and date=?3", nativeQuery = true)
    Optional<Visit> findVisitByPatientAndDoctorAndDate(Integer patientId, Integer doctorId, LocalDateTime date);


}
