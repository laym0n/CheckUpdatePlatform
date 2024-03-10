package com.victor.kochnev.integration.telegram.client;

import com.victor.kochnev.core.integration.TelegramClient;
import com.victor.kochnev.integration.telegram.bot.CheckUpdateTelegramBot;
import com.victor.kochnev.integration.telegram.exception.TelegramIntegrationException;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@ConditionalOnProperty(name = "app.integration.telegram.enabled", havingValue = "true")
@Service
@RequiredArgsConstructor
public class TelegramClientImpl implements TelegramClient {
    private final CheckUpdateTelegramBot telegramBot;

    @Override
    public void sendMessage(String telegramInfo, String message) {
        try {
            telegramBot.execute(new SendMessage(telegramInfo, message));
        } catch (TelegramApiException e) {
            throw new TelegramIntegrationException(e);
        }
    }
}
