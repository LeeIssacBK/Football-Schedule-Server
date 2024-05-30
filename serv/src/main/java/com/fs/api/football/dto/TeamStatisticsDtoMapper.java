package com.fs.api.football.dto;

import com.fs.api.football.domain.TeamStatistics;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface TeamStatisticsDtoMapper {

    TeamStatisticsDtoMapper INSTANCE = Mappers.getMapper(TeamStatisticsDtoMapper.class);

    TeamStatistics toEntity(TeamStatisticsDto.Response dto);
    TeamStatisticsDto.Response toResponse(TeamStatistics teamStatistics);

    void update(TeamStatisticsDto.Response dto, @MappingTarget TeamStatistics teamStatistics);

}
