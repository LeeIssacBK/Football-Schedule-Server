package com.fs.api.alert.dto;

import com.fs.api.alert.domain.Alert;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface AlertDtoMapper {

    AlertDtoMapper INSTANCE = Mappers.getMapper(AlertDtoMapper.class);

    AlertDto.Response toResponse(Alert alert);

    List<AlertDto.Response> toResponses(List<Alert> alerts);

}
