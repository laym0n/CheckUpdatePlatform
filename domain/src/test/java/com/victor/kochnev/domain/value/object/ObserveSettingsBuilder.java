package com.victor.kochnev.domain.value.object;

public class ObserveSettingsBuilder {
    public static final String DEFAULT_EMAIL = "victor_k02@mail.ru";
    public static final String DEFAULT_TELEGRAM = "victor_k02";

    private ObserveSettingsBuilder() {
    }

    public static ObserveSettings defaultObserveSettings() {
        return new ObserveSettings(DEFAULT_EMAIL, DEFAULT_TELEGRAM);
    }
}
