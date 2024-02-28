package com.fs.api.user.domain;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoutAccessTokenRepository extends CrudRepository<LogoutAccessToken, String> {


}
