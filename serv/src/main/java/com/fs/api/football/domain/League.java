package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseDomain;
import jakarta.persistence.*;
import lombok.*;


@EqualsAndHashCode(callSuper = true)
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
@Entity
public class League extends BaseDomain {

    public enum Type {
        LEAGUE, CUP
    }
    @Column(unique = true)
    private long apiId;
    private String name;
    private String logo;
    @Enumerated(value = EnumType.STRING)
    private Type type;
    @ManyToOne
    private Country country;

}
