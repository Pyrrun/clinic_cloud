package com.website.demo.API.patient;

import com.website.demo.API.person.PersonRepository;
import com.website.demo.API.visit.Visit;
import lombok.AllArgsConstructor;
import lombok.Data;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Optional;

@Service
@AllArgsConstructor
@Data
public class PatientService {

    private final PatientRepository patientRepository;
    private final PersonRepository personRepository;

    public List<Patient> getPatients() {
        return patientRepository.findAll();
    }

    public Optional<Patient> findPatientById(Integer id) {
        return patientRepository.findById(id);
    }

//    public void addPatientList() {
//        patientRepository.saveAll(new PatientFactory().getPatientList(10));
//    }
//
//    public void addPatient() {
//        patientRepository.save(new PatientFactory().getPatient());
//    }

    public void deleteById(Integer id) {
        patientRepository.deleteById(id);
    }




    public void removeDoctorForPatient(Integer patientId, Integer doctorId) {
        patientRepository.removeDoctorForPatient(doctorId, patientId);
    }

    public void addDoctorForPatient(Integer doctorId, Integer patientId) {
        patientRepository.addDoctorForPatient(doctorId, patientId);
        //return patientRepository.findById(patientID).get();
    }

    public List<PatientDto> getPatientsBy(String character, Integer doctorID) {
        if(character != null){
            return patientRepository.findByFirstNameStartsWith(character);
        }
        if(doctorID != null){
            return patientRepository.findByDoctorId(doctorID);
        }
        return patientRepository.findAllPatients();

    }

    public LocalDate birthdayToLocalDate(String birthdateString){
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        return LocalDate.parse(birthdateString, formatter);
    }

    @Transactional
    public void addNewPatient(Patient patient) {
        personRepository.save(patient.getPerson());
        patientRepository.save(patient);
    }
}
