package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.NotificationRepository;
import com.victor.kochnev.domain.entity.Notification;
import org.springframework.stereotype.Repository;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {
    @Override
    public Notification create(Notification notification) {
        return null;
    }
}
