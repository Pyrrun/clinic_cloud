package com.website.demo.API.dispensary;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
@Data
public class DispensaryService {

    private final DispensaryRepository dispensaryRepository;

    public List<Dispensary> getDispensaries() {
        return dispensaryRepository.findAll();
    }


}
