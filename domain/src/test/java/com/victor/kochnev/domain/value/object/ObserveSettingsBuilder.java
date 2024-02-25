package com.victor.kochnev.domain.value.object;

public class ObserveSettingsBuilder {

    private ObserveSettingsBuilder() {
    }

    public static ObserveSettings defaultObserveSettings() {
        return new ObserveSettings(true);
    }
}
