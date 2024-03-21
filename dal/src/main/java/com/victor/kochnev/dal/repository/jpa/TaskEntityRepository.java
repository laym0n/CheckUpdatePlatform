package com.victor.kochnev.dal.repository.jpa;

import com.victor.kochnev.dal.entity.TaskEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface TaskEntityRepository extends JpaRepository<TaskEntity, UUID> {
}
