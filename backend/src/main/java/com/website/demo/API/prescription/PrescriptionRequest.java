package com.website.demo.API.prescription;

import com.website.demo.API.medication.MedicationRequest;
import lombok.Data;

@Data
public class PrescriptionRequest {

    private int code;
    private MedicationRequest[] medications;

}
