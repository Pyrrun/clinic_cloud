package com.website.demo.API.message;

import com.website.demo.API.user.AppUser;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "message")
@Entity(name = "Message")
@Data
@NoArgsConstructor
public class Message {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String content;
    @DateTimeFormat(pattern = "yyyy-mm-dd hh:mm:ss")
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "sender_id")
    private AppUser sender;
    @ManyToOne
    @JoinColumn(name = "receiver_id")
    private AppUser receiver;

    public Message(String content,
                   LocalDateTime date,
                   AppUser sender,
                   AppUser receiver) {
        this.content = content;
        this.date = date;
        this.sender = sender;
        this.receiver = receiver;
    }
}
