package com.website.demo.API.visit.response;

import com.website.demo.API.examination.ExaminationDto;
import com.website.demo.API.prescription.PrescriptionDto;
import com.website.demo.API.referral.Referral;
import com.website.demo.API.visit.VisitDto;
import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class FinishedVisitResponse extends VisitResponse {

    private VisitDto visit;
    private List<Referral> referrals;
    private PrescriptionDto prescription;
    private ExaminationDto examination;


}
