package com.website.demo.API.message;

import lombok.Data;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@Data
@CrossOrigin
public class MessageController {


    private final MessageService messageService;




    @PostMapping("new-message")
    public void sendMessage(@RequestBody MessageRequest messageRequest){
         messageService.sendMessage(
                messageRequest.getContent(),
                messageRequest.getDate(),
                messageRequest.getReceiverId(),
                messageRequest.getSenderId()
        );

    }


    @GetMapping("messages")
    public List<MessageDto> getMessagesBy(@RequestParam(required = false) Integer receiverId,
                                       @RequestParam(required = false) Integer senderId){
        return messageService.findMessagesBy(receiverId, senderId);
    }

    @GetMapping("users/{id}/messages")
    public List<MessageDto> getMessagesBy(@PathVariable Integer id){
        return messageService.findMessagesForUser(id);
    }

}
