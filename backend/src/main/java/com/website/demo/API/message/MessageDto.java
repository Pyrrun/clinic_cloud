package com.website.demo.API.message;

import lombok.Data;

import java.time.LocalDateTime;

@Data
public class MessageDto {

    private String content;
    private LocalDateTime date;
    private Integer senderId;
    private Integer receiverId;

    public MessageDto(String content,
                      LocalDateTime date,
                      Integer senderId,
                      Integer receiverId){
        this.content = content;
        this.date = date;
        this.senderId = senderId;
        this.receiverId = receiverId;
    }


}
