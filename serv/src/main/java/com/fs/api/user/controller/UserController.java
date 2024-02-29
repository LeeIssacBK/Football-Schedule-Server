package com.fs.api.user.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Tag(name = "2. 회원")
@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/user")
public class UserController {

    @PostMapping("/join")
    public ResponseEntity<?> join() {
        return ResponseEntity.ok("");
    }



}

