package com.website.demo.API.referral;

import lombok.Data;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@Data
public class ReferralService {

    private final ReferralRepository referralRepository;

    public List<Referral> getReferralsBy(Integer visitId){
        return referralRepository.findByVisit_id(visitId);
    }

}
