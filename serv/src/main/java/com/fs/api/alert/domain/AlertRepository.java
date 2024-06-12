package com.fs.api.alert.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AlertRepository extends BaseRepository<Alert> {

    List<Alert> findAllByToUserIdOrderByFixtureDate(String userId);

    Optional<Alert> findByToUserIdAndFixtureApiId(String userId, long fixtureId);

    @Modifying
    @Query(value = "update alert set is_send = true where id in (:ids)", nativeQuery = true)
    void updateAllByIsSendTrue(@Param("ids") List<Long> ids);

}
