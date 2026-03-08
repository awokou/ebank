package com.server.api.ebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.api.ebank.domain.entity.History;

public interface HistoryRepository extends JpaRepository<History, Integer> {
}
