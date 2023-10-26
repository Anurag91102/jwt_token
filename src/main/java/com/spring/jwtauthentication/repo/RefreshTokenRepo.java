package com.spring.jwtauthentication.repo;

import java.util.Optional;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;
import com.spring.jwtauthentication.model.RefreshToken;

@Repository
public interface RefreshTokenRepo  extends CrudRepository<RefreshToken, Integer>
{
	Optional<RefreshToken> findByRefreshToken(String refreshToken);
}
