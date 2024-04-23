package com.fs.api.football.domain;

import com.fs.common.enums.Continent;
import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
public class Country extends BaseDomain {

    private String code;
    private String name;
    private String krName;
    private String flag;
    @Enumerated(EnumType.STRING)
    private Continent continent;

}
