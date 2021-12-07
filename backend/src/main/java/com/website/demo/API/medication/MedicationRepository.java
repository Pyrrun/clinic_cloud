package com.website.demo.API.medication;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Repository
public interface MedicationRepository extends JpaRepository<Medication, Integer> {
    List<Medication> findByNameStartsWith(String character);

    @Transactional
    @Modifying
    @Query(value = "insert into prescription_medication(prescription_id, medication_id) values (?1, ?2)", nativeQuery = true)
    void addMedicationToPrescription(Integer prescriptionId, Integer medicationId);
}
