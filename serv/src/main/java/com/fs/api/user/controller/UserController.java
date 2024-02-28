package com.fs.api.user.controller;

import com.fs.api.user.dto.UserDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.annotation.Secured;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/api/user")
public class UserController {

    @Secured({"ROLE_ADMIN", "ROLE_USER"})
    @GetMapping
    public UserDto.Simple getMe() {
        return null;
    }


}
