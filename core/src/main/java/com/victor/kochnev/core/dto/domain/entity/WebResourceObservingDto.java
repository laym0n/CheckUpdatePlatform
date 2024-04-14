package com.victor.kochnev.core.dto.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.core.dto.domain.value.object.ObserveSettingsDto;
import com.victor.kochnev.domain.enums.ObserveStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebResourceObservingDto {
    @JsonProperty("observeSettings")
    @NotNull
    private ObserveSettingsDto observeSettings;
    @JsonProperty("webResourceDto")
    @NotNull
    private WebResourceDto webResource;
    @JsonProperty("status")
    @NotNull
    private ObserveStatus status;
}
