package com.website.demo.API.prescription;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface PrescriptionRepository extends JpaRepository<Prescription, Integer> {

    Optional<Prescription> findByVisit_id(Integer id);
}
