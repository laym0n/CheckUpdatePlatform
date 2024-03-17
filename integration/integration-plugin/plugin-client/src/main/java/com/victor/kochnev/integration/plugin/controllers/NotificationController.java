package com.victor.kochnev.integration.plugin.controllers;

import com.victor.kochnev.core.dto.request.SendNotificationRequestDto;
import com.victor.kochnev.core.facade.notification.NotificationFacade;
import com.victor.kochnev.core.security.entity.PluginSecurity;
import com.victor.kochnev.core.security.service.plugin.PluginSecurityService;
import com.victor.kochnev.integration.plugin.api.NotificationApi;
import com.victor.kochnev.integration.plugin.api.dto.NotificationCreateRequestBody;
import com.victor.kochnev.integration.plugin.converter.PluginRequestMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@Slf4j
@RequiredArgsConstructor
@Validated
public class NotificationController implements NotificationApi {
    private static final String NOTIFICATION_ENDPOINT = "POST /notification";
    private final NotificationFacade notificationFacade;
    private final PluginSecurityService pluginSecurityService;
    private final PluginRequestMapper pluginRequestMapper;

    @Override
    public ResponseEntity<Void> create(NotificationCreateRequestBody requestBody) {
        log.info("Get {} ", NOTIFICATION_ENDPOINT);
        log.debug("Get {} Body {}", NOTIFICATION_ENDPOINT, requestBody);
        SendNotificationRequestDto requestDto = pluginRequestMapper.mapToCore(requestBody);

        PluginSecurity authenticatedPlugin = pluginSecurityService.getAuthenticatedPlugin();
        requestDto.setPluginId(authenticatedPlugin.getId());

        notificationFacade.sendNotification(requestDto);
        log.debug("Success execute {}", NOTIFICATION_ENDPOINT);
        return ResponseEntity.ok().build();
    }
}
