package com.website.demo.API.notice;

import com.website.demo.API.user.doctor.DoctorDto;
import lombok.Data;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Data
public class NoticeDto {

    private String title;
    private String content;
    private DoctorDto doctor;
    private LocalDateTime date;

    public static NoticeDto from(Notice notice){
        NoticeDto noticeDto = new NoticeDto();
        noticeDto.title = notice.getTitle();
        noticeDto.content = notice.getContent();
        noticeDto.doctor = DoctorDto.from(notice.getAppUser());
        noticeDto.date = notice.getDate();
        return noticeDto;
    }
}
