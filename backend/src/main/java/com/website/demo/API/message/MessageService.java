package com.website.demo.API.message;

import com.website.demo.API.user.AppUser;
import com.website.demo.API.user.AppUserRepository;
import lombok.Data;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;
import java.util.Locale;

@Service
@Data
public class MessageService {

    private final MessageRepository messageRepository;
    private final AppUserRepository appUserRepository;

    public void sendMessage(String content,
                            String date,
                            Integer receiverId,
                            Integer senderId) {
        // we don't want empty messages in our db
        if (content.length() == 0) {
            return;
        }
        AppUser sender = appUserRepository.findById(senderId).orElseThrow(IllegalStateException::new);
        AppUser receiver = appUserRepository.findById(receiverId).orElseThrow(IllegalStateException::new);
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm", Locale.ENGLISH);
        LocalDateTime localDateTime = LocalDateTime.parse(date, formatter);
        Message message = new Message(
                content,
                localDateTime,
                sender,
                receiver
        );
        messageRepository.save(message);

    }


    public List<MessageDto> findMessagesBy(Integer receiverId, Integer senderId) {
//        if(receiverId == null && senderId == null){
//            return messageRepository.findAll();
//        }
        return messageRepository.findMessagesBy(receiverId, senderId);

    }

    public List<MessageDto> findMessagesForUser(Integer id) {
        return messageRepository.findMessagesForUser(id);
    }
}
