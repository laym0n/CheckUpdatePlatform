package com.victor.kochnev.dal.repository.jpa;

import com.victor.kochnev.dal.entity.PluginEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface PluginEntityRepository extends JpaRepository<PluginEntity, UUID> {

    @Query("""
            select p from PluginEntity p
            join WebResourceEntity wr on wr.plugin.id = p.id
            where wr.id = :webResourceId
            """)
    Optional<PluginEntity> findByWebResourceId(@Param("webResourceId") UUID webResourceId);

    Optional<PluginEntity> findByAccessToken(String accessToken);
}
