package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.Entity;
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

}
