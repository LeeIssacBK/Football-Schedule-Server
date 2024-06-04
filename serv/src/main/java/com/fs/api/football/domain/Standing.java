package com.fs.api.football.domain;

import com.fs.api.football.dto.StandingDto;
import com.fs.configs.jpa.base.BaseDomain;
import com.fs.configs.jpa.converters.MapAttributeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Standing extends BaseDomain {

    @ManyToOne(fetch = FetchType.LAZY)
    public Team team;

    private int rank;
    private int points;
    private int goalsDiff;
    private String group;
    private String form;
    private String status;
    private String description;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapAttributeConverter.class)
    private StandingDto.Response.Standing.Game all;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapAttributeConverter.class)
    private StandingDto.Response.Standing.Game home;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapAttributeConverter.class)
    private StandingDto.Response.Standing.Game away;

    private LocalDateTime updateAt;

}
