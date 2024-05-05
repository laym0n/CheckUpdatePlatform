package com.victor.kochnev.domain.enums;

import lombok.AllArgsConstructor;

@AllArgsConstructor
public enum TaskDecision {
    APPROVE(false),
    REJECT(false),
    REJECT_BY_CREATOR(true);
    private boolean isForCreator;

    public static boolean isForCreator(TaskDecision taskDecision) {
        return taskDecision.isForCreator;
    }
}
