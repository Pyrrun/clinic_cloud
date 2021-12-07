package com.website.demo.API.specialization;

import com.website.demo.API.dispensary.Dispensary;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Repository
public interface SpecializationRepository extends JpaRepository<Specialization, Long> {

    @Query(value = "select * from specialization where id in(\n" +
            "    select specialization_id from dispensary_specialization where dispensary_id = ?1\n" +
            ")", nativeQuery = true)
    List<Specialization> findSpecializationsForDispensary(Integer id);

    @Query(value = "select * from specialization where name = ?1 and id in (\n" +
            "    select specialization_id from dispensary_specialization where dispensary_id in  (\n" +
            "        select id from dispensary where name = ?2\n" +
            "    ) \n" +
            ")", nativeQuery = true)
    Specialization findByNameAndDispensaryName(String name, String dispensaryName);


    @Query(value = "select id from specialization where name = ?1", nativeQuery = true)
    Optional<Long> findIdByName(String name);

    @Transactional
    @Modifying
    @Query(value = "insert into doctor_specialization(app_user_id, specialization_id) values (?1, ?2)", nativeQuery = true)
    void setSpecializationForDoctor(Integer doctorId, Integer specializationId);
}
