package com.fs.api.football.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "5. 국가")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/country")
public class TeamController {
}
