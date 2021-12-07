package com.website.demo.API.notice;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class NoticeRequest {

    private String title;
    private String content;
    private Integer doctorId;
    private String date;

}
