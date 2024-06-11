package com.fs.api.user.service;

import com.fs.api.user.domain.Device;
import com.fs.api.user.domain.DeviceRepository;
import com.fs.api.user.domain.User;
import com.fs.api.user.domain.UserRepository;
import com.fs.api.user.dto.DeviceDto;
import com.fs.common.exceptions.NotFoundException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Transactional
@Slf4j
@RequiredArgsConstructor
@Service
public class DeviceService {

    private final UserRepository userRepository;
    private final DeviceRepository deviceRepository;

    public ResponseEntity save(String userId, DeviceDto.Request request) {
        User user = userRepository.findByUserId(userId).orElseThrow(() -> new NotFoundException("user"));
        deviceRepository.findByUuid(request.getUuid()).ifPresentOrElse(
                device -> {
                    device.setUser(user);
                    device.setFcmToken(request.getFcmToken());
                },
                () -> deviceRepository.save(Device.builder()
                                .user(user)
                                .uuid(request.getUuid())
                                .platform(request.getPlatform())
                                .fcmToken(request.getFcmToken())
                        .build())
        );
        return ResponseEntity.ok().build();
    }


}

