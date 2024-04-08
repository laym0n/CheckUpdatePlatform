package com.victor.kochnev.core.dto.domain.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class PluginDomainDto {
    private UUID id;
    private String name;
    private String baseUrl;
}
