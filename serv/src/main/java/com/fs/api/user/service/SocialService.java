package com.fs.api.user.service;

import com.fs.api.auth.dto.TokenDto;

import java.util.Map;

public interface SocialService {

    TokenDto.Token login(String code);

    TokenDto.Token login(Map auth);

}
