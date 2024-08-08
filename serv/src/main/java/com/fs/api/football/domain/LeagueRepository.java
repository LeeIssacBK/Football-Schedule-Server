package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface LeagueRepository extends BaseRepository<League> {
    Optional<League> findByApiId(long apiId);

    Optional<List<League>> findAllByCountryCode(String code);

    @Modifying
    @Query(value = "update team t " +
                     "join season s " +
                       "on s.league_id = t.league_id " +
                      "set t.season_id = s.id " +
                    "where s.`current` is true", nativeQuery = true)
    void updateSeasons();

}
