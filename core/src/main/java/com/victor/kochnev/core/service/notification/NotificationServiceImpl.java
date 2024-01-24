package com.victor.kochnev.core.service.notification;

import com.victor.kochnev.core.dto.request.SendNotificationRequestDto;
import com.victor.kochnev.core.repository.NotificationRepository;
import com.victor.kochnev.domain.entity.Notification;
import com.victor.kochnev.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    public void sendNotification(SendNotificationRequestDto request, User user) {
        Notification notification = new Notification();
        notification.setUser(user);
        notification.setMessage(request.getMessage());
        log.debug("Create new notification {}", notification);
        notificationRepository.create(notification);
    }
}
