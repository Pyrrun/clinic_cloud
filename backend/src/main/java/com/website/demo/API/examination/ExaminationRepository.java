package com.website.demo.API.examination;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ExaminationRepository extends JpaRepository<Examination, Integer> {



    Optional<Examination> findByVisit_id(Integer id);
}
