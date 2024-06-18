package com.fs.api.qna.dto;

import com.fs.api.qna.domain.Qna;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Mapper
public interface QnaDtoMapper {

    QnaDtoMapper INSTANCE = Mappers.getMapper(QnaDtoMapper.class);

    QnaDto.Response toResponse(Qna qna);

    List<QnaDto.Response> toResponse(List<Qna> qna);

}
