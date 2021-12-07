//package com.website.demo.API.patient;
//
//import com.github.javafaker.Faker;
//
//import java.util.ArrayList;
//import java.util.List;
//import java.util.Locale;
//
//public class PatientFactory {
//
//    private final Faker faker = new Faker(new Locale("pl_PL"));
//
//    public List<Patient> getPatientList(int quantity){
//        List<Patient> patientList = new ArrayList<>();
//        for (int i = 0; i < quantity; i++) {
//                Patient patient = new Patient(faker.name().firstName(), faker.name().firstName(), faker.name().lastName());
//                patientList.add(patient);
//        }
//        return patientList;
//    }
//
//    public Patient getPatient(){
//        return new Patient(faker.name().firstName(), faker.name().firstName(), faker.name().lastName());
//    }
//
//}
