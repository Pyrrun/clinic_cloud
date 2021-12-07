package com.website.demo.API.notice;

import com.website.demo.cloudinary.CloudinaryConfig;
import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@Data
@CrossOrigin
public class NoticeController {

    private final NoticeService noticeService;



    @GetMapping("yoooo")
    public String hey(@RequestParam(defaultValue = "Roman") String name){
        return noticeService.sayHello(name);
    }



    @GetMapping("notices")
    public List<NoticeDto> findNotices(){
        return noticeService.findNotices().stream().map(NoticeDto::from).collect(Collectors.toList());
    }

    @GetMapping("notices/after")
    public List<NoticeDto> findNoticesAfter(@RequestParam String date){
        return noticeService.findNoticesAfter(date).stream().map(NoticeDto::from).collect(Collectors.toList());
    }

    @GetMapping("notices/before")
    public List<NoticeDto> findNoticesBefore(@RequestParam String date){
        return noticeService.findNoticesBefore(date).stream().map(NoticeDto::from).collect(Collectors.toList());
    }

    @PostMapping("notices")
    public void addNotice(@RequestBody NoticeRequest noticeRequest){
       NoticeDto noticeDto = NoticeDto.from(noticeService.addNotice(
               noticeRequest.getTitle(),
               noticeRequest.getContent(),
               noticeRequest.getDoctorId(),
               noticeRequest.getDate()
       ));
    }


}
