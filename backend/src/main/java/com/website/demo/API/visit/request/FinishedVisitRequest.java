package com.website.demo.API.visit.request;

import com.website.demo.API.medication.Medication;
import com.website.demo.API.medication.MedicationRequest;
import com.website.demo.API.patient.Patient;
import com.website.demo.API.prescription.PrescriptionRequest;
import com.website.demo.API.referral.Referral;
import com.website.demo.API.referral.ReferralRequest;
import lombok.Data;

@Data
public class FinishedVisitRequest {

    private Integer patientId;
    private Integer doctorId;
    private String interview;
    private String research;
    private ReferralRequest[] referrals;
    private PrescriptionRequest prescriptionRequest;
    private Integer[] medicationIds;
    private int duration;
    private String date;

}
