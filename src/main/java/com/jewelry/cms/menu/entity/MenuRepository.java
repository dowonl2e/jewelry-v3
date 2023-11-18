package com.jewelry.cms.menu.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuRepository extends JpaRepository<Menu, String>, MenuRepositoryCustom {
}
