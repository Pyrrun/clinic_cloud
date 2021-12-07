package com.website.demo.webSocket;

import lombok.Data;

@Data
public class WebSocketMessage {

    private String date;
    private String content;
    private Integer senderId;
    private Integer receiverId;

}
