package com.fs.api.user.service;

import com.fs.api.auth.dto.TokenDto;

public interface SocialService {

    TokenDto.Token login(String code);

}
