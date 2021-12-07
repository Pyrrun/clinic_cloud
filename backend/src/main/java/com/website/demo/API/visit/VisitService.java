package com.website.demo.API.visit;

//import com.website.demo.doctor.DoctorRepository;

import com.website.demo.API.dispensary.Dispensary;
import com.website.demo.API.dispensary.DispensaryRepository;
import com.website.demo.API.examination.Examination;
import com.website.demo.API.examination.ExaminationDto;
import com.website.demo.API.examination.ExaminationRepository;
import com.website.demo.API.medication.MedicationRepository;
import com.website.demo.API.patient.Patient;
import com.website.demo.API.patient.PatientRepository;
import com.website.demo.API.prescription.Prescription;
import com.website.demo.API.prescription.PrescriptionDto;
import com.website.demo.API.prescription.PrescriptionRepository;
import com.website.demo.API.referral.Referral;
import com.website.demo.API.referral.ReferralRepository;
import com.website.demo.API.referral.ReferralRequest;
import com.website.demo.API.schedule.Schedule;
import com.website.demo.API.schedule.ScheduleService;
import com.website.demo.API.specialization.Specialization;
import com.website.demo.API.specialization.SpecializationRepository;
import com.website.demo.API.user.AppUser;
import com.website.demo.API.user.AppUserRepository;
import com.website.demo.API.visit.response.AvailableHoursResponse;
import com.website.demo.API.visit.response.FinishedVisitResponse;
import com.website.demo.API.visit.response.VisitResponse;
import lombok.Data;
import org.springframework.data.crossstore.ChangeSetPersister;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Data
public class VisitService {

    private final VisitRepository visitRepository;
    private final ScheduleService scheduleService;
    private final PatientRepository patientRepository;
    private final AppUserRepository appUserRepository;
    private final DispensaryRepository dispensaryRepository;
    private final ReferralRepository referralRepository;
    private final MedicationRepository medicationRepository;
    private final ExaminationRepository examinationRepository;
    private final PrescriptionRepository prescriptionRepository;
    private final SpecializationRepository specializationRepository;


    public List<Visit> getAllVisits() {
        return visitRepository.findAll();
    }

    public List<Visit> findAllVisitsForOneDoctorByDate(LocalDate localDate, Integer doctorId) {
        return visitRepository.findAllVisitsForOneDoctorByDate(localDate, doctorId);
    }


    public AvailableHoursResponse getAvailableVisitHoursForDoctorByDate(String date, Integer doctorId) {
        Schedule schedule = scheduleService.findSchedulesForDoctorBy(date, doctorId).get(0);
        LocalTime startHour = LocalTime.parse(schedule.getStartHour());
        LocalTime endHour = LocalTime.parse(schedule.getEndHour());
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
        LocalDate localDate = LocalDate.parse(date, formatter);
        List<Visit> visitList = findAllVisitsForOneDoctorByDate(localDate, doctorId);
        List<LocalTime> visitHourList = new ArrayList<>();
        if (!visitList.isEmpty()) {
            for (Visit visit : visitList) {
                int hour = visit.getDate().getHour();
                int minute = visit.getDate().getMinute();
                LocalTime visitHour = LocalTime.of(hour, minute);
                visitHourList.add(visitHour);
            }
        }
        List<LocalTime> hourList = new ArrayList<>();
        while (startHour.isBefore(endHour)) {
            if (!visitHourList.contains(startHour)) {
                hourList.add(startHour);
            }
            startHour = startHour.plusMinutes(schedule.getVisitDuration() + schedule.getBreakDuration());
        }
        return new AvailableHoursResponse(hourList, schedule.getVisitDuration());
    }


    public List<Visit> getAllVisitsForPatient(Integer patientID, Boolean finished) {
        if (finished != null) {
            return visitRepository.findByPatient_idAndFinished(patientID, finished);
        }
        return visitRepository.findByPatient_id(patientID);


    }


    public List<Visit> getVisitsForDoctorBy(Integer doctorId,
                                            String date,
                                            Integer patientId,
                                            Boolean finished) {
        if (date != null && doctorId != null) {
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd", Locale.ENGLISH);
            LocalDate localDate = LocalDate.parse(date, formatter);
            if (finished != null) {
                return visitRepository.findAllVisitsForOneDoctorByDateAndFinished(localDate, doctorId, finished);
            }
            return visitRepository.findAllVisitsForOneDoctorByDate(localDate, doctorId);

        }
        if (patientId != null && doctorId != null) {
            if (finished != null) {
                return visitRepository.findByPatient_idAndDoctor_IdAndFinished(patientId, doctorId, finished);
            }
            return visitRepository.findByPatient_idAndDoctor_Id(patientId, doctorId);
        }
        return visitRepository.findByDoctor_Id(doctorId);
    }


    @Transactional
    public void saveFinishedVisit(String date,
                                  Integer doctorId,
                                  Integer patientId,
                                  String interview,
                                  Integer[] medicationIds,
                                  ReferralRequest[] referrals,
                                  String research) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        Visit visit = visitRepository.findVisitByPatientAndDoctorAndDate(patientId, doctorId, localDateTime).orElseThrow(() -> new NoSuchElementException("Visit not found"));
        Examination examination = new Examination(interview, research, visit);
        examinationRepository.save(examination);
        for (ReferralRequest referralRequest : referrals) {
            Dispensary dispensary = dispensaryRepository.findByNameAndSpecializationName(
                    referralRequest.getDispensary(),
                    referralRequest.getSpeciality()
            ).orElseThrow(() -> new NoSuchElementException("Dispensary not found"));
            Specialization specialization = specializationRepository.findByNameAndDispensaryName(referralRequest.getSpeciality(), referralRequest.getDispensary());
            int priority = 0;
            if (referralRequest.getPriority().equals("Urgent")) {
                priority = 1;
            }
            Referral referral = new Referral(
                    dispensary,
                    specialization,
                    visit,
                    referralRequest.getReason(),
                    priority
            );
            referralRepository.save(referral);
        }
        Prescription prescription = new Prescription("1111", visit);
        prescriptionRepository.save(prescription);
        for (Integer medicationId : medicationIds) {
            medicationRepository.addMedicationToPrescription(prescription.getId(), medicationId);
        }
        visit.setFinished(true);
        visitRepository.save(visit);
    }

    public List<VisitResponse> getFullVisitData(List<Visit> visitList) {
        List<VisitResponse> visitResponseList = new ArrayList<>();
        for (Visit visit : visitList) {
            Optional<Examination> examination = examinationRepository.findByVisit_id(visit.getId());
            ExaminationDto examinationDto = null;
            PrescriptionDto prescriptionDto = null;
            if (examination.isPresent()) {
                examinationDto = ExaminationDto.from(examination.get());
            }
            List<Referral> referralList = referralRepository.findByVisit_id(visit.getId());
            Optional<Prescription> prescription = prescriptionRepository.findByVisit_id(visit.getId());
            if (prescription.isPresent()) {
                prescriptionDto = PrescriptionDto.from(prescription.get());
            }
            FinishedVisitResponse visitResponse = new FinishedVisitResponse(
                    VisitDto.from(visit),
                    referralList,
                    prescriptionDto,
                    examinationDto
            );
            visitResponseList.add(visitResponse);
        }
        return visitResponseList;

    }


    public List<VisitResponse> getFullVisitDataForDoctorBy(Integer doctorId, String date, Integer patientId, Boolean finished) {
        List<Visit> visitList = getVisitsForDoctorBy(doctorId, date, patientId, finished);
        if (finished == null || finished) {
            return getFullVisitData(visitList);
        }
        return visitList.stream().map(VisitDto::from).collect(Collectors.toList());

    }


    public List<VisitResponse> getFullVisitDataForPatient(Integer patientId, Boolean finished) {
        List<Visit> visitList = getAllVisitsForPatient(patientId, finished);
        return getFullVisitData(visitList);
    }

    public void addNewUnfinishedVisit(Integer patientId, Integer doctorId, String dateString, int duration) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss", Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.parse(dateString, formatter);
        visitRepository.saveNewVisit(localDateTime, duration, doctorId, patientId);
    }


    public Visit getVisitsByDate(Integer patientId, Integer doctorId, String date) {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        return visitRepository.findVisitByPatientAndDoctorAndDate(patientId, doctorId, localDateTime).orElseThrow(() -> new NoSuchElementException("Visit not found"));
    }
}
