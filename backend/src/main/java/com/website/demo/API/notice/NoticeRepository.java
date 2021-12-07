package com.website.demo.API.notice;

import com.website.demo.API.patient.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NoticeRepository extends JpaRepository<Notice, Long> {
    @Query(value = "select * from notice order by date desc limit 4", nativeQuery = true)
    List<Notice> findTopFour();

    @Query(value = "select * from notice where date < ?1 order by date desc limit 4", nativeQuery = true)
    List<Notice> findBeforeDate(String date);
    @Query(value = "select * from notice where date > ?1 order by date desc limit 4 ", nativeQuery = true)
    List<Notice> findAfterDate(String date);
}
