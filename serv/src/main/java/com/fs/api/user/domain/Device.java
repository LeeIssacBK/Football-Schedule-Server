package com.fs.api.user.domain;

import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;

@Entity
public class Device extends BaseDomain {

    @Column(unique = true)
    private String uuid;

}
