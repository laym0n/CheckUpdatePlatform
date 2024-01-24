package com.victor.kochnev.core.repository;

import com.victor.kochnev.domain.entity.Notification;

public interface NotificationRepository {
    Notification create(Notification notification);
}
