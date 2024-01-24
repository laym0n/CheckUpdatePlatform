package com.victor.kochnev.core.facade.notification;

import com.victor.kochnev.core.dto.request.SendNotificationRequestDto;

public interface NotificationFacade {
    void sendNotification(SendNotificationRequestDto request);
}
