package com.victor.kochnev.core.dto.request;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class StopWebResourceObservingRequest {
    private UUID webResourceObservingId;
    private UUID userId;
}