package com.victor.kochnev.core.dto.request;

import com.victor.kochnev.domain.enums.TaskDecision;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.UUID;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MakeDecisionRequestDto {
    private UUID taskId;
    private TaskDecision decision;
    private String comment;
}
