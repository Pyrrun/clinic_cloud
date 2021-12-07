package com.website.demo.API.notice;

import com.website.demo.API.user.AppUser;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Table(name = "notice")
@Entity(name = "Notice")
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter

public class Notice {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String title;
    private String content;
    private LocalDateTime date;
    @ManyToOne
    @JoinColumn(name = "doctor_id")
    private AppUser appUser;

    public Notice(String title, String content, LocalDateTime date,  AppUser appUser){
        this.title = title;
        this.content = content;
        this.appUser = appUser;
        this.date = date;
    }


}
