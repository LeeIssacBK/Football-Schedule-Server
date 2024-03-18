package com.fs.api.football.dto;

import com.fs.api.football.domain.League;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface LeagueDtoMapper {

    LeagueDtoMapper INSTANCE = Mappers.getMapper(LeagueDtoMapper.class);

    List<LeagueDto.AppResponse> toAppResponse(List<League> leagues);

}
