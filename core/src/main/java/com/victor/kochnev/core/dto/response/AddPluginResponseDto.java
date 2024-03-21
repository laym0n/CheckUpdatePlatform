package com.victor.kochnev.core.dto.response;

import com.victor.kochnev.core.dto.domain.entity.PluginDto;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddPluginResponseDto {
    private PluginDto plugin;
    private String accessToken;
}
