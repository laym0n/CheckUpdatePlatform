package com.victor.kochnev.core.dto.request;

import com.victor.kochnev.domain.value.object.ObserveSettings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;
import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddWebResourceForObservingRequest {
    private UUID pluginId;
    private String resourceDescription;
    private UUID userId;
    private Optional<ObserveSettings> observeSettings;
}
