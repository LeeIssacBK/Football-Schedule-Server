package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
public class Fixture extends BaseDomain {

    public enum Status {
        NS, TBD, //NOT STARTED
        FT, AET, PEN  //FINISHED
    }

    @Column(unique = true)
    private long apiId;
    private String round;
    @Enumerated(value = EnumType.STRING)
    private Status status;
    private String referee;
    private LocalDateTime date;
    @ManyToOne
    private League league;
    @ManyToOne
    private Team home;
    @ManyToOne
    private Team away;

}
