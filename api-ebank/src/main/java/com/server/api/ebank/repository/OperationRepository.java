package com.server.api.ebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.server.api.ebank.domain.entity.Operations;

import java.util.List;

public interface OperationRepository extends JpaRepository<Operations, Integer> {

    List<Operations> findByAccountIdOrderByIdDesc(Integer accountId);

    @Query(value = "SELECT * FROM operation o WHERE o.account_id = ?1 AND favorite = 'true'", nativeQuery = true)
    List<Operations> findFavoriteOperations(Integer accountId);

    @Query(value = "SELECT * FROM operation o WHERE o.id = :id", nativeQuery = true)
    Operations oneFavoriteOperation(Integer id);
}
