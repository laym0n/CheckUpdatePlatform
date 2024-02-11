package com.victor.kochnev.core.dto.domain.value.object;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ObserveSettingsDto {
    private String email;
    private String telegram;
}
