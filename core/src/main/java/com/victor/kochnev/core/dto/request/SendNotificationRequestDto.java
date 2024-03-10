package com.victor.kochnev.core.dto.request;

import com.victor.kochnev.core.dto.plugin.NotificationPluginDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SendNotificationRequestDto {
    private UUID pluginId;
    private NotificationPluginDto notification;
    private WebResourcePluginDto updatedResource;
}
