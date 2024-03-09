package com.victor.kochnev.core.facade.notification;

import com.victor.kochnev.core.dto.plugin.NotificationPluginDto;
import com.victor.kochnev.domain.entity.WebResourceObserving;

import java.util.List;

public interface NotificationHandler {
    void notify(List<WebResourceObserving> observingList, NotificationPluginDto notificationPluginDto);
}
