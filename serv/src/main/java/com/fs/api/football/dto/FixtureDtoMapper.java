package com.fs.api.football.dto;

import com.fs.api.football.domain.Fixture;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface FixtureDtoMapper {

    FixtureDtoMapper INSTANCE = Mappers.getMapper(FixtureDtoMapper.class);

    List<FixtureDto.AppResponse> toAppResponse(List<Fixture> fixtures);

}
