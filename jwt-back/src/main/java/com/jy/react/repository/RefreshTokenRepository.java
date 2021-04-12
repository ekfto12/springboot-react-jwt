package com.jy.react.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jy.react.domain.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Long>{
	Optional<RefreshToken> findByKey(String key);
}
