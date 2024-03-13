package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import lombok.*;

@EqualsAndHashCode(callSuper = true)
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
public class Team extends BaseDomain {

    @ManyToOne
    private League league;
    @ManyToOne
    private Season season;
    @Column(unique = true)
    private long apiId;
    private String name;
    private String code;
    private int founded;
    private boolean national;
    private String logo;
    private String stadium;
    private String address;
    private String city;
    private int capacity;
    private String stadiumImage;

}
