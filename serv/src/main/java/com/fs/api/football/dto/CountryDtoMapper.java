package com.fs.api.football.dto;

import com.fs.api.football.domain.Country;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface CountryDtoMapper {

    CountryDtoMapper INSTANCE = Mappers.getMapper(CountryDtoMapper.class);

    List<CountryDto.AppResponse> toAppResponse(List<Country> countries);

}
