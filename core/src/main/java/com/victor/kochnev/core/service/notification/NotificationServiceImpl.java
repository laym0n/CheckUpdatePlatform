package com.victor.kochnev.core.service.notification;

import com.victor.kochnev.core.dto.plugin.NotificationPluginDto;
import com.victor.kochnev.core.repository.NotificationRepository;
import com.victor.kochnev.domain.entity.Notification;
import com.victor.kochnev.domain.entity.User;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationServiceImpl implements NotificationService {
    private final NotificationRepository notificationRepository;

    @Override
    @Transactional
    public void createNotifications(List<User> userList, NotificationPluginDto notificationDto) {
        List<Notification> notificationList = userList.stream()
                .map(user -> {
                    Notification notification = new Notification();
                    notification.setUser(user);
                    notification.setMessage(notificationDto.getMessage());
                    log.debug("Create new notification {}", notification);
                    return notification;
                }).toList();
        notificationRepository.createAll(notificationList);
    }
}
