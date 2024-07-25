package com.fs.api.football.domain;

import com.fs.api.football.dto.StandingDto;
import com.fs.configs.jpa.base.BaseDomain;
import com.fs.configs.jpa.converters.columns.StandingConverter;
import jakarta.persistence.*;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Standing extends BaseDomain {

    @ManyToOne(fetch = FetchType.EAGER)
    public Team team;

    private Integer _rank;
    private Integer points;
    private Integer goalsDiff;
    private String _group;
    private String form;
    private String _status;
    private String description;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StandingConverter.class)
    private StandingDto.Response.Standing.Game _all;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StandingConverter.class)
    private StandingDto.Response.Standing.Game home;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = StandingConverter.class)
    private StandingDto.Response.Standing.Game away;

}
