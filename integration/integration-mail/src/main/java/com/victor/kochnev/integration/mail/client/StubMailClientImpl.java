package com.victor.kochnev.integration.mail.client;

import com.victor.kochnev.core.integration.MailClient;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.integration.mail.enabled", havingValue = "false", matchIfMissing = true)
@Slf4j
public class StubMailClientImpl implements MailClient {

    @Override
    public void sendMail(String emailTo, String message) {
        log.info("Send message " + message + " to email emailTo");
    }
}
