package com.jy.react.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.jy.react.domain.User;

public interface UserRepository extends JpaRepository<User, Long>{

	Optional<User> findByUid(String uid);
	boolean existsByUid(String uid);

}
