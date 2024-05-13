package com.fs.api.alert.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlertRepository extends BaseRepository<Alert> {

    List<Alert> findAllByToUserId(String userId);

}
