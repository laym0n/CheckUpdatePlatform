package com.victor.kochnev.core.facade.notification;

import com.victor.kochnev.core.dto.request.SendNotificationRequestDto;
import com.victor.kochnev.core.service.notification.NotificationService;
import com.victor.kochnev.core.service.webresource.WebResourceService;
import com.victor.kochnev.core.service.webresourceobserving.WebResourceObservingService;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
@RequiredArgsConstructor
@Slf4j
public class NotificationFacadeImpl implements NotificationFacade {
    private final NotificationService notificationService;
    private final WebResourceObservingService webResourceObservingService;
    private final WebResourceService webResourceService;
    private final List<NotificationHandler> notificationHandlerList;

    @Override
    public void sendNotification(SendNotificationRequestDto request) {
        webResourceService.update(request.getPluginId(), request.getUpdatedWebResourceDto());
        List<WebResourceObserving> observingList = webResourceObservingService.findAllActualObservers(request.getUpdatedWebResourceDto().getName());
        List<User> userList = observingList.stream().map(WebResourceObserving::getUser).toList();
        notificationService.createNotifications(userList, request.getNotificationDto());
        notificationHandlerList.forEach(notificationHandler -> notificationHandler.notify(observingList, request.getNotificationDto()));
    }
}
