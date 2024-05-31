package com.fs.api.user.service;


import com.fs.api.user.domain.User;
import com.fs.api.user.domain.User.SocialType;
import com.fs.api.user.dto.KakaoDto;
import com.fs.api.user.domain.UserRepository;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Service
public class UserService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepository userRepository;


    @Transactional
    public User createOrFindByUser(KakaoDto.Auth auth) {
        User user = userRepository.findByUserIdAndSocialType(String.valueOf(auth.getId()), SocialType.KAKAO)
                .orElseGet(() -> userRepository.save(User.builder()
                        .socialType(SocialType.KAKAO)
                        .userId(String.valueOf(auth.getId()))
                        .password(passwordEncoder.encode(auth.getId() + auth.getConnected_at()))
                        .roles(List.of("ROLE_USER")).build()));
        user.setName(auth.getProperties().getNickname());
        user.setProfileImage(auth.getProperties().getProfile_image());
        return user;
    }

}
