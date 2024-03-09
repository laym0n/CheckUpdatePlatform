package com.victor.kochnev.dal.repository.jpa;

import com.victor.kochnev.dal.entity.PluginUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface PluginUsageEntityRepository extends JpaRepository<PluginUsageEntity, UUID>, JpaSpecificationExecutor<PluginUsageEntity> {

}
