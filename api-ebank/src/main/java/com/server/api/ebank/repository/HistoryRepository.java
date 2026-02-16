package com.server.api.ebank.repository;

import com.server.api.ebank.entity.History;
import org.springframework.data.jpa.repository.JpaRepository;

public interface HistoryRepository extends JpaRepository<History, Integer> {
}
