package com.fs.api.user.domain;

import com.fs.configs.jpa.base.BaseRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface DeviceRepository extends BaseRepository<Device> {

    Optional<Device> findByUuid(String uuid);

    void deleteAllByUser(User user);

}
