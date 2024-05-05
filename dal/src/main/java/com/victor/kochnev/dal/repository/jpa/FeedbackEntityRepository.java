package com.victor.kochnev.dal.repository.jpa;

import com.victor.kochnev.dal.entity.FeedbackEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface FeedbackEntityRepository extends JpaRepository<FeedbackEntity, UUID> {

    @Query("""
            select f from FeedbackEntity f
            where f.user.id = :userId and f.plugin.id = :pluginId
            """)
    Optional<FeedbackEntity> findByUserIdAndPluginId(@Param("userId") UUID userId, @Param("pluginId") UUID pluginId);
}
