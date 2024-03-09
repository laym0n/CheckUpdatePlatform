package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.Notification;

import java.util.List;

public interface NotificationRepository {
    Notification createAll(List<Notification> notification);
}
