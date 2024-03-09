package com.victor.kochnev.core.service.handler;

import com.victor.kochnev.core.dto.plugin.NotificationPluginDto;
import com.victor.kochnev.core.facade.notification.NotificationHandler;
import com.victor.kochnev.core.integration.MailClient;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MailNotificationHandler implements NotificationHandler {
    private final MailClient mailClient;

    @Override
    public void notify(List<WebResourceObserving> observingList, NotificationPluginDto notificationPluginDto) {
        observingList.forEach(observing -> {
            mailClient.sendMail(observing.getUser().getEmail(), notificationPluginDto.getMessage());
        });
    }
}
