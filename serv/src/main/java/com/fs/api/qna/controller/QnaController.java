package com.fs.api.qna.controller;

import com.fs.api.qna.dto.QnaDto;
import com.fs.api.qna.service.QnaService;
import com.fs.api.support.service.SupportService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Tag(name = "9. Q & A")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/qna")
public class QnaController {

    private final QnaService qnaService;

    @Operation(summary = "Q & A 내용을 가져온다.")
    @PostMapping
    public ResponseEntity<List<QnaDto.Response>> get() {
        return ResponseEntity.ok(qnaService.get());
    }

}
