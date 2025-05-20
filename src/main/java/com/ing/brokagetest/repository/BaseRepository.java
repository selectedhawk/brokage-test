package com.ing.brokagetest.repository;

import com.ing.brokagetest.entity.BaseEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface BaseRepository<ENTITY extends BaseEntity> extends JpaSpecificationExecutor<ENTITY>, JpaRepository<ENTITY, Long> {
}
