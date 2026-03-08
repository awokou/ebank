package com.server.api.ebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.api.ebank.domain.entity.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Integer> {

    Optional<User> findByEmail(String email);

    boolean existsByEmail(String email);
}
