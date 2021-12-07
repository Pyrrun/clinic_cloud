package com.website.demo.API.user;

import com.website.demo.API.specialization.Specialization;
import com.website.demo.API.specialization.SpecializationDto;
import com.website.demo.API.user.doctor.DoctorDto;
import com.website.demo.API.user.doctor.DoctorName;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.Set;

@Repository
public interface AppUserRepository extends JpaRepository<AppUser, Integer> {

    @Query("select u from AppUser u where u.person.email = ?1")
    Optional<AppUser> findByEmail(String email);

    @Query("select u.person.firstName from AppUser u where u.person.email = ?1")
    Optional<String> findFirstNameByEmail(String email);


   // boolean existsByEmail(String email);

    @Query("select u.specializations from AppUser u where u.id = ?1")
    Set<Specialization> getSpecializationsForDoctor(Integer id);


    @Query("select new com.website.demo.API.user.doctor.DoctorDto(a, p) from AppUser a inner join Person p on p.id = a.person.id where a.role = 'DOCTOR'")
    List<DoctorDto> findAllDoctors();


  //  Optional<AppUser> findByEmailAndPassword(String email, String password);


    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.enabled = true WHERE a.person.id = (SELECT p.id FROM Person p WHERE p.email = ?1)")
    void enableAppUser(String email);

    @Modifying
    @Query("UPDATE AppUser a " +
            "SET a.img = ?1 WHERE a.person.id = (SELECT p.id FROM Person p WHERE p.email = ?2)")
    void setImg(String img, String email);

    @Query("select new com.website.demo.API.user.doctor.DoctorDto(a, a.person) from AppUser a join a.schedules schedule where a.role = 'DOCTOR' and schedule.date = ?1")
    List<DoctorDto> findAllByScheduleDate(LocalDate date);


    @Query("select new com.website.demo.API.user.AppUserDto(a) from AppUser a where a.id <> ?1")
    List<AppUserDto> findAllExceptFor(Integer id);


    @Modifying
    @Query(value = "INSERT INTO doctor_schedule(app_user_id, schedule_id) VALUES (?2, ?1)", nativeQuery = true)
    void setSchedule(Integer scheduleId, Integer doctorId);

    @Query("select new com.website.demo.API.user.doctor.DoctorDto(a.person.firstName, a.person.secondName, a.person.lastName) from AppUser a where a.id = ?1")
    DoctorName getDoctorName(Integer id);

    @Query("select new com.website.demo.API.user.doctor.DoctorDto(a, p) from AppUser a join Person p on p.id = a.person.id where lower(p.firstName) like lower(concat(:name,'%')) and a.role = 'DOCTOR'")
    List<DoctorDto> findByFirstNameStartsWith(String name);

}
