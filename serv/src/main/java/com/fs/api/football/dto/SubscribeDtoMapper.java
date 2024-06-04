package com.fs.api.football.dto;

import com.fs.api.football.domain.League;
import com.fs.api.football.domain.Subscribe;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubscribeDtoMapper {

    SubscribeDtoMapper INSTANCE = Mappers.getMapper(SubscribeDtoMapper.class);

    List<SubscribeDto.Response> toResponses(List<Subscribe> subscribe);

    @Mapping(source = "subscribe.team.league", target = "league", qualifiedByName = "mapLeague")
    SubscribeDto.Response toResponse(Subscribe subscribe);

    @Named("mapLeague")
    default LeagueDto.AppResponse mapLeague(League league) {
        return LeagueDtoMapper.INSTANCE.toAppResponse(league);
    }

}
