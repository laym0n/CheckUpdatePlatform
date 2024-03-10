package com.victor.kochnev.integration.plugin.controllers;

import com.victor.kochnev.core.dto.request.SendNotificationRequestDto;
import com.victor.kochnev.core.facade.notification.NotificationFacade;
import com.victor.kochnev.integration.plugin.api.NotificationApi;
import com.victor.kochnev.integration.plugin.api.dto.NotificationCreateRequestBody;
import com.victor.kochnev.integration.plugin.converter.PluginRequestMapper;
import com.victor.kochnev.integration.plugin.security.entity.PluginSecurity;
import com.victor.kochnev.integration.plugin.security.service.PluginSecurityService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class NotificationController implements NotificationApi {
    private final NotificationFacade notificationFacade;
    private final PluginSecurityService pluginSecurityService;
    private final PluginRequestMapper pluginRequestMapper;

    @Override
    public ResponseEntity<Void> create(NotificationCreateRequestBody requestBody) {
        SendNotificationRequestDto requestDto = pluginRequestMapper.mapToCore(requestBody);

        PluginSecurity authenticatedPlugin = pluginSecurityService.getAuthenticatedPlugin();
        requestDto.setPluginId(authenticatedPlugin.getId());

        notificationFacade.sendNotification(requestDto);
        return ResponseEntity.ok().build();
    }
}
