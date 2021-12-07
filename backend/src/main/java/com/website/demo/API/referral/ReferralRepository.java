package com.website.demo.API.referral;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ReferralRepository extends JpaRepository<Referral, Integer> {
    List<Referral> findByVisit_id(Integer visitId);
}
