package com.jewelry.cms.code.entity;

import org.springframework.data.jpa.repository.JpaRepository;

public interface CodeRepository extends JpaRepository<Code, String>, CodeRepositoryCustom {
}
