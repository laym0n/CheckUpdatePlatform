package com.victor.kochnev.integration.telegram.client;

import com.victor.kochnev.core.integration.TelegramClient;
import com.victor.kochnev.integration.telegram.bot.CheckUpdateTelegramBot;
import com.victor.kochnev.integration.telegram.exception.TelegramIntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;

@ConditionalOnProperty(name = "app.integration.telegram.enabled", havingValue = "true")
@Service
@RequiredArgsConstructor
@Slf4j
public class TelegramClientImpl implements TelegramClient {
    private final CheckUpdateTelegramBot telegramBot;

    @Override
    public void sendMessage(String telegramInfo, String message) {
        SendMessage telegramMessage = new SendMessage(telegramInfo, message);
        log.info("Send telegram {}", telegramMessage);
        try {
            telegramBot.execute(telegramMessage);
        } catch (TelegramApiException e) {
            String msg = ExceptionUtils.getMessage(e);
            log.error("Send telegram {} with error {}", telegramInfo, msg);
            throw new TelegramIntegrationException(msg, e);
        }
        log.info("Send telegram {} successful", telegramInfo);
    }
}
