package com.fs.api.football.dto;

import com.fs.api.football.domain.Standing;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;
import org.mapstruct.factory.Mappers;

@Mapper
public interface StandingDtoMapper {

    StandingDtoMapper INSTANCE = Mappers.getMapper(StandingDtoMapper.class);

    void update(StandingDto.Response.Standing dto, @MappingTarget Standing standing);

}
