package com.website.demo.API.patient;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Repository
public interface PatientRepository extends JpaRepository<Patient, Integer> {



    @Transactional
    @Modifying
    @Query(value= "update patient set doctor_id = null where doctor_id = ?1 and id = ?2", nativeQuery = true)
    void removeDoctorForPatient(Integer doctor_id, Integer id);

    @Transactional
    @Modifying
    @Query(value = "update patient set doctor_id = ?1 where id = ?2", nativeQuery = true)
    void addDoctorForPatient(Integer doctor_id, Integer id);


    @Query("select new com.website.demo.API.patient.PatientDto(p) from Patient p")
    List<PatientDto> findAllPatients();


    @Query("select new com.website.demo.API.patient.PatientDto(p) from Patient p where lower(p.person.firstName) like lower(concat(:character, '%'))")
    List<PatientDto> findByFirstNameStartsWith(String character);

    @Query("select new com.website.demo.API.patient.PatientDto(p) from Patient p where p.appUser.id = ?1")
    List<PatientDto> findByDoctorId(Integer doctorId);

}
