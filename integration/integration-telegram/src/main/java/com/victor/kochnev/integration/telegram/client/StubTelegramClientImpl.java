package com.victor.kochnev.integration.telegram.client;


import com.victor.kochnev.core.integration.TelegramClient;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@ConditionalOnProperty(name = "app.integration.telegram.enabled", havingValue = "false", matchIfMissing = true)
@Service
@RequiredArgsConstructor
@Slf4j
public class StubTelegramClientImpl implements TelegramClient {
    @Override
    public void sendMessage(String telegramInfo, String message) {
        log.info("Send message " + message + " to Telegram " + telegramInfo);
    }
}
