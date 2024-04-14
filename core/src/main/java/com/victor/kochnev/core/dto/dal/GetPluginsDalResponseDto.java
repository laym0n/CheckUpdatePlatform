package com.victor.kochnev.core.dto.dal;

import com.victor.kochnev.domain.entity.Plugin;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GetPluginsDalResponseDto {
    private List<Plugin> plugins;
}
