package com.victor.kochnev.integration.plugin.controllers;

import com.victor.kochnev.core.facade.notification.NotificationFacade;
import com.victor.kochnev.integration.plugin.api.NotificationApi;
import com.victor.kochnev.integration.plugin.api.dto.NotificationCreateRequestBody;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Validated
public class NotificationController implements NotificationApi {
    private final NotificationFacade notificationFacade;

    @Override
    public ResponseEntity<Void> create(NotificationCreateRequestBody requestBody) {
//        PluginSecurity pluginSecurity = (PluginSecurity) SecurityContextHolder.getContext().getAuthentication().getPrincipal();
//        SendNotificationRequestDto requestDto = new SendNotificationRequestDto();
//
//        NotificationPluginDto notificationPluginDto = new NotificationPluginDto();
//        notificationPluginDto.setMessage(requestBody.getNotification().getMessage());
//        requestDto.setNotificationDto(notificationPluginDto);
//
//        WebResourcePluginDto webResourcePluginDto = new WebResourcePluginDto();
//        webResourcePluginDto.setName(requestBody.getUpdatedResource().getName());
//        webResourcePluginDto.setDescription(requestBody.getUpdatedResource().getDescription());
//        requestDto.setUpdatedWebResourceDto(webResourcePluginDto);
//        requestDto.setPluginId(pluginSecurity.getId());
//
//        notificationFacade.sendNotification(requestDto);
        return ResponseEntity.ok().build();
    }
}
