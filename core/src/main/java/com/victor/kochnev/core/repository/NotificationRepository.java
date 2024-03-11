package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.Notification;

import java.util.List;

public interface NotificationRepository {
    void createAll(List<Notification> notificationList);
}
