package com.website.demo.API.examination;

import com.website.demo.API.visit.Visit;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Data
@Table(name = "examination")
@Entity(name="Examination")
@NoArgsConstructor
public class Examination {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String interview;
    private String research;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "visit_id")
    private Visit visit;

    public Examination(String interview, String research, Visit visit){
        this.interview = interview;
        this.research = research;
        this.visit = visit;
    }
}
