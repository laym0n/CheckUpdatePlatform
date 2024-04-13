package com.victor.kochnev.core.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.core.dto.plugin.NotificationPluginDto;
import com.victor.kochnev.core.dto.plugin.WebResourcePluginDto;
import jakarta.validation.constraints.NotNull;
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
    @JsonProperty("decision")
    @NotNull
    private UUID pluginId;
    @JsonProperty("notification")
    @NotNull
    private NotificationPluginDto notification;
    @JsonProperty("updatedResource")
    @NotNull
    private WebResourcePluginDto updatedResource;
}
