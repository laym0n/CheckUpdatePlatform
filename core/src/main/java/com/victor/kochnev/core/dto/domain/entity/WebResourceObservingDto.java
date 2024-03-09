package com.victor.kochnev.core.dto.domain.entity;

import com.victor.kochnev.domain.enums.ObserveStatus;
import com.victor.kochnev.domain.value.object.ObserveSettings;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class WebResourceObservingDto {
    private ObserveSettings observeSettings;
    private WebResourceDto webResourceDto;
    private ObserveStatus status;
}
