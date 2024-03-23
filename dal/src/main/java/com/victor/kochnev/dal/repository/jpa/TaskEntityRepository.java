package com.victor.kochnev.dal.repository.jpa;

import com.victor.kochnev.dal.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.UUID;

@Repository
public interface TaskEntityRepository extends JpaRepository<TaskEntity, UUID> {

    @Query("select t from TaskEntity t where t.plugin.id = :pluginId")
    List<TaskEntity> findAllByPluginId(@Param("pluginId") UUID pluginId);
}
