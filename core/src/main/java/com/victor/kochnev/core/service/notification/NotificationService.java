package com.victor.kochnev.core.service.notification;

import com.victor.kochnev.core.dto.plugin.NotificationPluginDto;
import com.victor.kochnev.domain.entity.User;

import java.util.List;

public interface NotificationService {

    void createNotifications(List<User> userList, NotificationPluginDto notificationDto);
}
