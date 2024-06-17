package com.fs.api.user.service;

import com.fs.api.alert.domain.AlertRepository;
import com.fs.api.football.domain.SubscribeRepository;
import com.fs.api.user.domain.User;
import com.fs.api.user.domain.UserRepository;
import com.fs.api.user.dto.MyInfoDto;
import com.fs.api.user.dto.UserDto;
import com.fs.common.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Transactional(readOnly = true)
@RequiredArgsConstructor
@Service
public class MyInfoService {

    private final UserRepository userRepository;
    private final AlertRepository alertRepository;
    private final SubscribeRepository subscribeRepository;

    public MyInfoDto.Response getMyInfo(UserDto.Simple dto) {
        User user = userRepository.findByUserId(dto.getUserId()).orElseThrow(() -> new NotFoundException("user"));
        return MyInfoDto.Response.builder()
                .socialType(user.getSocialType())
                .subscribeTeamCount(subscribeRepository.countByUserAndDeleteIsFalse(user))
                .totalAlertsCount(alertRepository.countByTo(user))
                .build();
    }

}
