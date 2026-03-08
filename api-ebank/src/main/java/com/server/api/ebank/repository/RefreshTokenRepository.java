package com.server.api.ebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.api.ebank.domain.entity.RefreshToken;

public interface RefreshTokenRepository extends JpaRepository<RefreshToken, Integer> {
}
