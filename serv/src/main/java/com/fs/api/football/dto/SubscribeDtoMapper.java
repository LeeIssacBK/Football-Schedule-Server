package com.fs.api.football.dto;

import com.fs.api.football.domain.Subscribe;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface SubscribeDtoMapper {

    SubscribeDtoMapper INSTANCE = Mappers.getMapper(SubscribeDtoMapper.class);

    List<SubscribeDto.Response> toResponses(List<Subscribe> subscribe);

}
