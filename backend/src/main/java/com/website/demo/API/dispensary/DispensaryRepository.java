package com.website.demo.API.dispensary;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DispensaryRepository extends JpaRepository<Dispensary, Integer> {

    @Query(value = "select * from dispensary where name = ?1 and id in (\n" +
            "    select dispensary_id from dispensary_specialization where specialization_id in  (\n" +
            "        select id from specialization where name = ?2\n" +
            "    ) \n" +
            ")", nativeQuery = true)
    Optional<Dispensary> findByNameAndSpecializationName(String name, String specializationName);
}
