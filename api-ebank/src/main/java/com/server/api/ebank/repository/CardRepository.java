package com.server.api.ebank.repository;

import com.server.api.ebank.entity.Card;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CardRepository extends JpaRepository<Card, Integer> {

    Card findByCustomerId(Integer id);
}
