package com.fs.api.football.dto;

import com.fs.api.football.domain.Team;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface TeamDtoMapper {

    TeamDtoMapper INSTANCE = Mappers.getMapper(TeamDtoMapper.class);

    List<TeamDto.AppResponse> toAppResponse(List<Team> teams);

}
