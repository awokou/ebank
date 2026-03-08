package com.server.api.ebank.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.server.api.ebank.domain.entity.Card;

public interface CardRepository extends JpaRepository<Card, Integer> {

    Card findByCustomerId(Integer id);
}
