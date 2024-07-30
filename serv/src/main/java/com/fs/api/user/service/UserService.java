package com.fs.api.user.service;


import com.fs.api.alert.domain.AlertRepository;
import com.fs.api.football.domain.SubscribeRepository;
import com.fs.api.user.domain.DeviceRepository;
import com.fs.api.user.domain.User;
import com.fs.api.user.domain.User.SocialType;
import com.fs.api.user.dto.KakaoDto;
import com.fs.api.user.domain.UserRepository;
import com.fs.api.user.dto.NaverDto;
import com.fs.api.user.dto.UserDto;
import com.fs.common.exceptions.NotFoundException;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.checkerframework.checker.units.qual.N;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;

@Transactional
@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;
    private final SubscribeRepository subscribeRepository;
    private final AlertRepository alertRepository;
    private final DeviceRepository deviceRepository;

    public User createOrFindByUser(KakaoDto.Auth auth) {
        User user = userRepository.findByUserIdAndSocialType(String.valueOf(auth.getId()), SocialType.KAKAO)
                .orElseGet(() -> userRepository.save(User.builder()
                        .socialType(SocialType.KAKAO)
                        .userId(String.valueOf(auth.getId()))
                        .password(passwordEncoder.encode(auth.getId() + auth.getConnected_at()))
                        .roles(List.of("ROLE_USER"))
                        .status(User.Status.ENABLED)
                        .build()));
        user.setName(auth.getProperties().getNickname());
        user.setProfileImage(auth.getProperties().getProfile_image());
        return user;
    }

    public User createOrFindByUser(NaverDto.Auth auth) {
        User user = userRepository.findByUserIdAndSocialType(String.valueOf(auth.getId()), SocialType.NAVER)
                .orElseGet(() -> userRepository.save(User.builder()
                        .socialType(SocialType.NAVER)
                        .userId(String.valueOf(auth.getId()))
                        .password(passwordEncoder.encode(auth.getId() + auth.getEmail()))
                        .roles(List.of("ROLE_USER"))
                        .status(User.Status.ENABLED)
                        .build()));
        user.setName(auth.getNickname());
        user.setProfileImage(auth.getProfileImage());
        return user;
    }

    public void withdraw(UserDto.Simple dto) {
        User user = userRepository.findByUserId(dto.getUserId()).orElseThrow(() -> new NotFoundException("user"));
        user.setStatus(User.Status.PENDING);
        user.setWithdrawAt(LocalDateTime.now());
    }

    public void deleteWithdrawUser() {
        //status bulk update
        userRepository.updateWithdrawUser();
        userRepository.findAllByStatus(User.Status.WITHDRAWAL).forEach(user -> {
            //subscribe
            subscribeRepository.deleteAllByUser(user);
            //alert
            alertRepository.deleteAllByTo(user);
            //device
            deviceRepository.deleteAllByUser(user);
            //user
            userRepository.deleteById(user.getId());
        });
    }

}
