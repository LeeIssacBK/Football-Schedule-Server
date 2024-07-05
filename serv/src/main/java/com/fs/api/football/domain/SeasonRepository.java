package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public interface SeasonRepository extends BaseRepository<Season> {
    @Transactional
    void deleteAllByLeague(League league);
    Optional<Season> findByLeagueAndCurrentIsTrue(League league);
}
