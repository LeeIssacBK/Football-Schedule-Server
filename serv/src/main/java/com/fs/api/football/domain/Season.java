package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.time.LocalDate;

@EqualsAndHashCode(callSuper = true)
@Data
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
