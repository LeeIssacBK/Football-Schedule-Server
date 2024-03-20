package com.fs.configs.security.domain;

import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import lombok.Getter;

@Getter
@Entity
public class Code extends BaseDomain {

    @Column(unique = true)
    private String k;
    private String value;

}
