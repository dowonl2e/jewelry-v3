package com.jewelry.cash.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CashRepository extends JpaRepository<Cash, Long>, CashRepositoryCustom {
}
