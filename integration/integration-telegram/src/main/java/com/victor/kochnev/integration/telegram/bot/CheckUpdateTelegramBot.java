package com.victor.kochnev.integration.telegram.bot;

import com.victor.kochnev.integration.telegram.config.TelegramConfigurationProperties;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Component;
import org.telegram.telegrambots.bots.TelegramLongPollingBot;
import org.telegram.telegrambots.meta.TelegramBotsApi;
import org.telegram.telegrambots.meta.api.methods.send.SendMessage;
import org.telegram.telegrambots.meta.api.objects.Update;
import org.telegram.telegrambots.meta.exceptions.TelegramApiException;
import org.telegram.telegrambots.updatesreceivers.DefaultBotSession;

@ConditionalOnProperty(name = "app.integration.telegram.enabled", havingValue = "true")
@Component
@RequiredArgsConstructor
public class CheckUpdateTelegramBot extends TelegramLongPollingBot {
    private final TelegramConfigurationProperties telegramConfigurationProperties;

    @SneakyThrows
    @Override
    public void onUpdateReceived(Update update) {
        this.execute(new SendMessage(Long.toString(update.getMessage().getChatId()), "Получено сообщение " + update.getMessage().getText()));
    }

    @Override
    public String getBotUsername() {
        return telegramConfigurationProperties.getBotUserName();
    }

    @Override
    public String getBotToken() {
        return telegramConfigurationProperties.getBotToken();
    }

    @PostConstruct
    private void botConnect() throws TelegramApiException {
        TelegramBotsApi telegramBotsApi = new TelegramBotsApi(DefaultBotSession.class);
        telegramBotsApi.registerBot(this);
    }
}
