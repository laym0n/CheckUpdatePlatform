package com.victor.kochnev.domain.value.object;

import java.time.Duration;
import java.time.temporal.ChronoUnit;

public class ObserveSettingsBuilder {
    public static final String DEFAULT_EMAIL = "victor_k02@mail.ru";
    public static final String DEFAULT_TELEGRAM = "victor_k02";
    public static final Duration DEFAULT_DURATION = Duration.of(10, ChronoUnit.DAYS);

    private ObserveSettingsBuilder() {
    }

    public static ObserveSettings defaultObserveSettings() {
        return new ObserveSettings(DEFAULT_EMAIL, DEFAULT_TELEGRAM);
    }
}
