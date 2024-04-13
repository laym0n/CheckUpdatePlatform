package com.victor.kochnev.core.dto.request;

import com.fasterxml.jackson.annotation.JsonProperty;
import com.victor.kochnev.domain.enums.TaskDecision;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MakeDecisionRequestDto {
    @JsonProperty("decision")
    @NotNull
    private TaskDecision decision;
    @JsonProperty("comment")
    private String comment;
}
