package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeagueRepository extends BaseRepository<League> {
    Optional<League> findByApiId(long apiId);

    Optional<List<League>> findAllByCountryCode(String code);
}
