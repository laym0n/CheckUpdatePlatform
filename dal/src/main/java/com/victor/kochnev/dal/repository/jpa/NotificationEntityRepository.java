package com.victor.kochnev.dal.repository.jpa;

import com.victor.kochnev.dal.entity.NotificationEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface NotificationEntityRepository extends JpaRepository<NotificationEntity, UUID> {
}
