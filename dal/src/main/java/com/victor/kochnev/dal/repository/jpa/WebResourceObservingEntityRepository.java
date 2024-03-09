package com.victor.kochnev.dal.repository.jpa;

import com.victor.kochnev.dal.entity.WebResourceObservingEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface WebResourceObservingEntityRepository extends JpaRepository<WebResourceObservingEntity, UUID>, JpaSpecificationExecutor<WebResourceObservingEntity> {

    @Query("""
            select wro from WebResourceObservingEntity wro
            where wro.user.id = :userId and wro.webResource.id = :webResourceId
            """)
    Optional<WebResourceObservingEntity> findByWebResourceIdAndUserId(@Param("webResourceId") UUID webResourceId, @Param("userId") UUID userId);
}
