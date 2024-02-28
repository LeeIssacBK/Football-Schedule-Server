package com.fs.api.user.domain;

import com.fs.configs.jpa.base.BaseDomain;
import com.fs.configs.jpa.converters.ListAttributeConverter;
import jakarta.persistence.Column;
import jakarta.persistence.Convert;
import jakarta.persistence.Entity;
import lombok.Getter;

import java.util.List;


@Getter
@Entity
public class User extends BaseDomain {

    @Column(nullable = false, unique = true)
    private String userId;

    private String password;

    private String name;

    @Convert(converter = ListAttributeConverter.class)
    private List<String> roles;

}

