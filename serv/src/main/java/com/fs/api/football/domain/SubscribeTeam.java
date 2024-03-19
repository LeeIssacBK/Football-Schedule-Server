package com.fs.api.football.domain;

import com.fs.api.user.domain.User;
import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;

@Entity
public class SubscribeTeam extends BaseDomain {

    @ManyToOne
    private Team team;

    @ManyToOne
    private User user;

}
