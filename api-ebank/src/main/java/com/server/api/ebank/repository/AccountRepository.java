package com.server.api.ebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.server.api.ebank.domain.entity.Account;

public interface AccountRepository extends JpaRepository<Account, Integer> {

    @Query(value = "SELECT * from account a WHERE a.customer_id = ?1 AND type = 'CA'", nativeQuery = true)
    Account findCurrentByCustomerId(Integer v);

    @Query(value = "SELECT * from account a WHERE a.customer_id = ?1 AND type = 'SA'", nativeQuery = true)
    Account findSavingByCustomerId(Integer v);
}
