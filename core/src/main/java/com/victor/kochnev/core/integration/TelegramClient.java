package com.victor.kochnev.core.integration;

public interface TelegramClient {
    void sendMessage(String telegramInfo, String message);
}
