package com.victor.kochnev.core.service.handler;

import com.victor.kochnev.core.dto.plugin.NotificationPluginDto;
import com.victor.kochnev.core.facade.notification.NotificationHandler;
import com.victor.kochnev.core.integration.TelegramClient;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
public class TelegramHandler implements NotificationHandler {
    private final TelegramClient telegramClient;

    @Override
    public void notify(List<WebResourceObserving> observingList, NotificationPluginDto notificationPluginDto) {
        observingList.stream()
                .map(observing -> observing.getUser().getTelegramInfo())
                .filter(Objects::nonNull)
                .forEach(telegramInfo -> {
                    telegramClient.sendMessage(telegramInfo, notificationPluginDto.getMessage());
                });
    }
}
