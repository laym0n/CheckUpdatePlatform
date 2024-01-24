package com.victor.kochnev.core.service.notification;

import com.victor.kochnev.core.dto.request.SendNotificationRequestDto;
import com.victor.kochnev.domain.entity.User;

public interface NotificationService {
    void sendNotification(SendNotificationRequestDto request, User user);
}
