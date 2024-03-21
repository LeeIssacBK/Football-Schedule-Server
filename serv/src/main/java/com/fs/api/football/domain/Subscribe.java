package com.fs.api.football.domain;

import com.fs.api.user.domain.User;
import com.fs.common.enums.SubscribeType;
import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.ManyToOne;
import lombok.*;
import org.hibernate.annotations.ColumnDefault;

@EqualsAndHashCode(callSuper = true)
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Entity
public class Subscribe extends BaseDomain {

    private SubscribeType type;

    @ManyToOne(fetch = FetchType.LAZY)
    private Team team;

    @ManyToOne(fetch = FetchType.LAZY)
    private Player player;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @ColumnDefault("false")
    private boolean isDelete;

}
