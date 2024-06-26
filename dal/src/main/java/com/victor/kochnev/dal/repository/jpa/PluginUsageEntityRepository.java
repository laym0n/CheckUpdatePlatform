package com.victor.kochnev.dal.repository.jpa;

import com.victor.kochnev.dal.entity.PluginUsageEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface PluginUsageEntityRepository extends JpaRepository<PluginUsageEntity, UUID>, JpaSpecificationExecutor<PluginUsageEntity> {

    @Query("""
            select pu from PluginUsageEntity pu
            where pu.user.id = :userId and pu.plugin.id = :pluginId
            """)
    List<PluginUsageEntity> findByUserIdAndPluginId(@Param("userId") UUID userId, @Param("pluginId") UUID pluginId);

    @Query("""
            select pu from PluginUsageEntity pu
            where pu.user.id = :userId and pu.plugin.id = :pluginId
            order by pu.expiredDate desc
            LIMIT 1
            """)
    Optional<PluginUsageEntity> findLastByExpiredDate(@Param("userId") UUID userId, @Param("pluginId") UUID pluginId);

    @Query("""
            select pu from PluginUsageEntity pu
            where pu.user.id = :userId and pu.plugin.id = :pluginId
            order by pu.expiredDate
            LIMIT 1
            """)
    Optional<PluginUsageEntity> findAnyByUserIdAndPluginId(@Param("userId") UUID userId, @Param("pluginId") UUID pluginId);
}
