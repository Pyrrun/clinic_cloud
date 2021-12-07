package com.website.demo.API.medication;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class MedicationService {

    private final MedicationRepository medicationRepository;

    public List<Medication> findMedicationsBy(String name) {
        return medicationRepository.findByNameStartsWith(name);
    }
}
