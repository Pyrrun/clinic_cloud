package com.website.demo.API.referral;

import lombok.Data;

@Data
public class ReferralDto {

    private String reason;
    private int priority;
    private String dispensary;
    private String specialization;

    public static ReferralDto from(Referral referral){
        ReferralDto referralDto = new ReferralDto();
        referralDto.reason = referral.getReason();
        referralDto.priority = referral.getPriority();
        referralDto.dispensary = referral.getDispensary().getName();
        return referralDto;
    }
}
