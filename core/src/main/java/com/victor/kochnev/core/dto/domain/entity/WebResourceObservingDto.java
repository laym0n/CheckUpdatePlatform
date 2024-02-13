package com.victor.kochnev.core.dto.domain.entity;

import com.victor.kochnev.domain.value.object.ObserveSettings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.Optional;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebResourceObservingDto {
    private Optional<ObserveSettings> observeSettings;
    private WebResourceDto webResourceDto;
}
