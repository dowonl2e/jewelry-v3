package com.jewelry.repair.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface RepairRepository extends JpaRepository<Repair, Long>, RepairRepositoryCustom {
}
