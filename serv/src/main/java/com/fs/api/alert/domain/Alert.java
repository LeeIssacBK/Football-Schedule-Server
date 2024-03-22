package com.fs.api.alert.domain;

import com.fs.api.user.domain.User;
import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;

@Entity
public class Alert extends BaseDomain {

    private String message;
    @ManyToOne(fetch = FetchType.LAZY)
    private User to;
    private boolean isSend;

}
