package com.fs.api.auth.repository;

import com.fs.api.auth.domain.LogoutAccessToken;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface LogoutAccessTokenRepository extends CrudRepository<LogoutAccessToken, String> {


}
