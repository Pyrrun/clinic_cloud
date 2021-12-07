package com.website.demo.API.notice;

import com.website.demo.API.user.AppUser;
import com.website.demo.API.user.AppUserRepository;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.AdditionalAnswers.returnsFirstArg;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.when;


public class NoticeServiceTest {



//
//    @Test
//    public void testNoticeSaving(){
//
//        NoticeRequest noticeRequest = new NoticeRequest("test", "content", 1, "2021-03-19 06:00:00");
//        NoticeRepository noticeRepository = Mockito.mock(NoticeRepository.class);
//        AppUserRepository appUserRepository = Mockito.mock(AppUserRepository.class);
//        NoticeService noticeService = new NoticeService(noticeRepository, appUserRepository);
//        AppUser appUser = new AppUser();
//        when(appUserRepository.findById(noticeRequest.getDoctorId())).thenReturn(Optional.of(appUser));
//        when(noticeRepository.save(any(Notice.class))).then(returnsFirstArg());
//        Notice notice = noticeService.addNotice(noticeRequest.getTitle(),
//                noticeRequest.getContent(),
//                noticeRequest.getDoctorId(),
//                noticeRequest.getDate());
//        assertEquals(notice.getContent(), "content");
//
//    }

}
