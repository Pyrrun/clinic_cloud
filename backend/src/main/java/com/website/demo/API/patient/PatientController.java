package com.website.demo.API.patient;

import com.website.demo.API.address.Address;
import com.website.demo.API.address.AddressService;
import com.website.demo.API.user.AppUser;
import com.website.demo.API.user.AppUserRepository;
import com.website.demo.API.user.AppUserService;
import com.website.demo.API.visit.Visit;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@CrossOrigin
@RestController
@Data
@RequiredArgsConstructor
@RequestMapping("patients")
public class PatientController {

    private final PatientService patientService;
    private final AppUserService appUserService;
    private final AddressService addressService;


    @PostMapping
    public void addNewPatient(@RequestBody NewPatientRequest newPatientRequest){

        Address requestAddress = new Address(
                newPatientRequest.getCountry(),
                newPatientRequest.getCity(),
                newPatientRequest.getStreet(),
                newPatientRequest.getFlatNumber(),
                newPatientRequest.getPostcode()
        );
        AppUser appUser = appUserService.findById(newPatientRequest.getDoctorId());
        Patient patient = new Patient(
                newPatientRequest.getFirstName(),
                newPatientRequest.getSecondName(),
                newPatientRequest.getLastName(),
                newPatientRequest.getPesel(),
                patientService.birthdayToLocalDate(newPatientRequest.getBirthdate()),
                newPatientRequest.getPhone(),
                newPatientRequest.getEmail(),
                addressService.getAddressAndSaveIfNotExists(requestAddress),
                appUser
        );
        patientService.addNewPatient(patient);
    }

    @GetMapping("{id}")
    public Optional<Patient> getPatient(@PathVariable Integer id) {
        return patientService.findPatientById(id);
    }


    @GetMapping
    public List<PatientDto> getPatientsBy(@RequestParam(required = false) String name, @RequestParam(required = false) Integer doctorId) {
        return patientService.getPatientsBy(name, doctorId);
    }


    @PatchMapping("{id}/remove-doctor")
    public void removeDoctorForPatient(@PathVariable Integer id, @RequestParam Integer doctorId) {
        patientService.removeDoctorForPatient(id, doctorId);
    }

    @PatchMapping("{id}/add-doctor")
    public void addDoctorForPatient(@PathVariable Integer id, @RequestParam Integer doctorId) {
        patientService.addDoctorForPatient(doctorId, id);
    }


    @DeleteMapping("{id}")
    public void deleteById(@PathVariable Integer id) {
        patientService.deleteById(id);
    }




}
