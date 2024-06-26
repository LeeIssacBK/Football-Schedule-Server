package com.fs.api.football.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface FixtureRepository extends BaseRepository<Fixture> {

    Optional<Fixture> findByApiId(long apiId);

    Optional<List<Fixture>> findAllByHomeOrAway(Team home, Team away);

    Optional<List<Fixture>> findAllByHomeOrAwayAndStatus(Team home, Team away, Fixture.Status status);

}
