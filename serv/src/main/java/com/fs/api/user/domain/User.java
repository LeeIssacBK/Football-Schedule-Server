package com.fs.api.user.domain;

import com.fs.configs.jpa.base.BaseDomain;
import com.fs.configs.jpa.converters.ListAttributeConverter;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;


@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseDomain {

    public enum SocialType {
        KAKAO,
        NAVER
    }

    public enum Status {
        ENABLED, //활성화
        WITHDRAWAL, //탈퇴
        PENDING //탈퇴대기
    }

    @Column(nullable = false, unique = true)
    private String userId;

    private String password;

    private String name;

    private String profileImage;

    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    @Convert(converter = ListAttributeConverter.class)
    private List<String> roles;

    @Enumerated(value = EnumType.STRING)
    private Status status;

    private LocalDateTime withdrawAt;

}

