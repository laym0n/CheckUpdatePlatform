package com.victor.kochnev.core.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.domain.enums.ObserveStatus;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class UpdateWebResourceObservingRequestDto {
    @JsonIgnore
    private UUID webResourceObservingId;
    @JsonProperty("status")
    @NotNull
    private ObserveStatus status;
}
