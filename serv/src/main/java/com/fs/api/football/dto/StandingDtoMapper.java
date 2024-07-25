package com.fs.api.football.dto;

import com.fs.api.football.domain.Standing;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StandingDtoMapper {

    StandingDtoMapper INSTANCE = Mappers.getMapper(StandingDtoMapper.class);

    void update(StandingDto.Response.Standing dto, @MappingTarget Standing standing);

//    @Mapping(source = "_all", target = "_all", qualifiedByName = "mapHashMap")
//    @Mapping(source = "home", target = "home", qualifiedByName = "mapHashMap")
//    @Mapping(source = "away", target = "away", qualifiedByName = "mapHashMap")
    StandingDto.AppResponse toAppResponse(Standing standing);

//    @Named("mapHashMap")
//    default StandingDto.Response.Standing.Game mapHashMap(Map map) {
//        return new ObjectMapper().convertValue(map, StandingDto.Response.Standing.Game.class);
//    }


}
