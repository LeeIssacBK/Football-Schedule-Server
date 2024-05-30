package com.fs.api.football.domain;

import com.fs.api.football.dto.TeamStatisticsDto;
import com.fs.configs.jpa.base.BaseDomain;
import com.fs.configs.jpa.converters.ListAttributeConverter;
import com.fs.configs.jpa.converters.MapAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

import java.util.List;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class TeamStatistics extends BaseDomain {

    @ManyToOne
    private Team team;
    private String form;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapAttributeConverter.class)
    private TeamStatisticsDto.Response.Fixtures fixtures;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapAttributeConverter.class)
    private TeamStatisticsDto.Response.Goals goals;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapAttributeConverter.class)
    private TeamStatisticsDto.Response.Biggest biggest;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapAttributeConverter.class)
    private TeamStatisticsDto.Response.Score clean_sheet;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapAttributeConverter.class)
    private TeamStatisticsDto.Response.Score failed_to_score;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapAttributeConverter.class)
    private TeamStatisticsDto.Response.Penalty penalty;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = ListAttributeConverter.class)
    private List<TeamStatisticsDto.Response.LineUp> lineups;

    @Column(columnDefinition = "TEXT")
    @Convert(converter = MapAttributeConverter.class)
    private TeamStatisticsDto.Response.Cards cards;

}
