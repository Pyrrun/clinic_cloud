package com.website.demo.API.specialization;

import com.website.demo.API.user.AppUser;
import com.website.demo.API.user.AppUserRepository;
import com.website.demo.API.visit.Visit;
import lombok.Data;

import org.json.JSONArray;
import org.json.JSONException;
import org.springframework.data.domain.Example;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Data
public class SpecializationService {

    private final SpecializationRepository specializationRepository;
    private final AppUserRepository appUserRepository;


    public void setSpecializationsForDoctor(AppUser appUser, String specializationNames){
        try {
            JSONArray array = new JSONArray(specializationNames);
            List<String> specializationNamesList = new ArrayList<>();
            for(int i = 0; i < array.length(); i++){
                specializationNamesList.add((String) array.get(i));
            }
            Set<Specialization> specializationSet = new HashSet<>();
            for(String specializationName : specializationNamesList){
                Example<Specialization> example = Example.of(new Specialization(specializationName));
                Specialization specialization =  specializationRepository
                        .findOne(example)
                        .orElseThrow(() -> new NoSuchElementException("Specialization with name " + specializationName + " not found"));
                specializationSet.add(specialization);
            }
            appUser.setSpecializations(specializationSet);
            appUserRepository.save(appUser);
        } catch (JSONException e) {
            e.printStackTrace();
        }

    }


    public List<Specialization> getAll() {
        return specializationRepository.findAll();
    }

    public List<Specialization> getSpecializationsForDispensary(Integer id) {
        return specializationRepository.findSpecializationsForDispensary(id);
    }
}
