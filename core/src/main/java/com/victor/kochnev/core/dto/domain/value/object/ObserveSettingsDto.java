package com.victor.kochnev.core.dto.domain.value.object;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@NoArgsConstructor
@AllArgsConstructor
public final class ObserveSettingsDto {
    @JsonProperty("needNotify")
    private boolean needNotify;
}
