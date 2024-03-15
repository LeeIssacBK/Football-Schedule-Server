package com.fs.api.football.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "6. 경기일정")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/fixture")
public class FixtureController {


}
