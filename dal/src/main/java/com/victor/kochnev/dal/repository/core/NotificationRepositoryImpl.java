package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.NotificationRepository;
import com.victor.kochnev.domain.entity.Notification;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public class NotificationRepositoryImpl implements NotificationRepository {
    @Override
    public Notification createAll(List<Notification> notification) {
        return null;
    }
}
