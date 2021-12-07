package com.website.demo.API.visit;

import com.website.demo.API.visit.request.FinishedVisitRequest;
import com.website.demo.API.visit.request.NewUnfinishedVisitRequest;
import com.website.demo.API.visit.response.AvailableHoursResponse;
import com.website.demo.API.visit.response.VisitResponse;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@Data
public class VisitController {

    private final VisitService visitService;



    @PostMapping("visits")
    public void addNewUnfinishedVisit(@RequestBody NewUnfinishedVisitRequest newUnfinishedVisitRequest){
        visitService.addNewUnfinishedVisit(
                newUnfinishedVisitRequest.getPatientId(),
                newUnfinishedVisitRequest.getDoctorId(),
                newUnfinishedVisitRequest.getDate(),
                newUnfinishedVisitRequest.getDuration()
        );
    }

    @PutMapping("visits")
    public void saveFinishedVisit(@RequestBody FinishedVisitRequest finishedVisitRequest) {
        visitService.saveFinishedVisit(
                finishedVisitRequest.getDate(),
                finishedVisitRequest.getDoctorId(),
                finishedVisitRequest.getPatientId(),
                finishedVisitRequest.getInterview(),
                finishedVisitRequest.getMedicationIds(),
                finishedVisitRequest.getReferrals(),
                finishedVisitRequest.getResearch()
                );
    }

    @GetMapping("doctors/{id}/visit-hours")
    @ResponseBody
    public AvailableHoursResponse getAvailableVisitHoursByDate(@PathVariable Integer id,
                                                               @RequestParam String date){
        return visitService.getAvailableVisitHoursForDoctorByDate(date, id);
    }

    @GetMapping("doctors/{id}/visits")
    @ResponseBody
    public List<VisitResponse> getVisitsForDoctorBy(@PathVariable Integer id,
                                                    @RequestParam(required = false) String date,
                                                    @RequestParam(required = false) Integer patientId,
                                                    @RequestParam(required = false) Boolean finished) {
        return visitService.getFullVisitDataForDoctorBy(id, date, patientId, finished);
    }

    @GetMapping("patients/{id}/visits")
    public List<VisitResponse> getAllVisitsForPatient(@PathVariable Integer id,
                                                      @RequestParam(required = false) Boolean finished){
        return visitService.getFullVisitDataForPatient(id, finished);
    }

    @GetMapping("visits")
    public Visit getVisitsByDate(@RequestParam Integer patientId, @RequestParam Integer doctorId, @RequestParam String date){
        return visitService.getVisitsByDate(patientId, doctorId, date);
    }








}
