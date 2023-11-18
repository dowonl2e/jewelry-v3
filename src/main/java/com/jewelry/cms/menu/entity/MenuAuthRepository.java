package com.jewelry.cms.menu.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface MenuAuthRepository extends JpaRepository<MenuAuth, MenuAuthID>, MenuAuthRepositoryCustom {
}
