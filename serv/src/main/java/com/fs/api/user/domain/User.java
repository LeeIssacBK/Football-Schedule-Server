package com.fs.api.user.domain;

import com.fs.configs.jpa.base.BaseDomain;
import com.fs.configs.jpa.converters.ListAttributeConverter;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.util.List;


@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class User extends BaseDomain {

    public enum SocialType {
        KAKAO, NAVER
    }

    @Column(nullable = false, unique = true)
    private String userId;

    private String password;

    private String name;

    @Enumerated(value = EnumType.STRING)
    private SocialType socialType;

    @Convert(converter = ListAttributeConverter.class)
    private List<String> roles;

}

