package com.website.demo.API.message;

import lombok.Data;

@Data
public class MessageRequest {

    private Integer senderId;
    private Integer receiverId;
    private String content;
    private String date;
}
