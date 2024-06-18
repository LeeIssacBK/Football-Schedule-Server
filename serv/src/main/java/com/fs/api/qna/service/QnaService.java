package com.fs.api.qna.service;

import com.fs.api.qna.domain.QnaRepository;
import com.fs.api.qna.dto.QnaDto;
import com.fs.api.qna.dto.QnaDtoMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class QnaService {

    private final QnaRepository qnaRepository;

    public List<QnaDto.Response> get() {
        return QnaDtoMapper.INSTANCE.toResponse(qnaRepository.findAllByDeleteIsFalse());
    }

}
