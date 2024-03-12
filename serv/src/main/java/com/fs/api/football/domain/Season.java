package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Season extends BaseDomain {

    @ManyToOne
    private League league;
    private int year;
    private LocalDate start;
    private LocalDate end;
    private boolean current;



}
