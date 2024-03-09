package com.victor.kochnev.dal.repository.jpa;

import com.victor.kochnev.dal.entity.WebResourceEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WebResourceEntityRepository extends JpaRepository<WebResourceEntity, UUID> {
    @Query("""
            select wr from WebResourceEntity wr
            where wr.name = :name and wr.plugin.id = :pluginId
            """)
    Optional<WebResourceEntity> findByNameAndPluginId(@Param("name") String name, @Param("pluginId") UUID pluginId);

    Optional<WebResourceEntity> findByName(String name);
}
