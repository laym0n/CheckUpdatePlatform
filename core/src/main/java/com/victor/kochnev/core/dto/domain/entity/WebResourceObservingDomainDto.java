package com.victor.kochnev.core.dto.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.domain.enums.ObserveStatus;
import com.victor.kochnev.domain.value.object.ObserveSettings;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebResourceObservingDomainDto {
    @JsonProperty("observeSettings")
    @NotNull
    private ObserveSettings observeSettings;
    @JsonProperty("webResourceDto")
    @NotNull
    private WebResourceDomainDto webResource;
    @JsonProperty("status")
    @NotNull
    private ObserveStatus status;
}
