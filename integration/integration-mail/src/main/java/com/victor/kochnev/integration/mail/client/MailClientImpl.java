package com.victor.kochnev.integration.mail.client;

import com.victor.kochnev.core.integration.MailClient;
import com.victor.kochnev.integration.mail.config.MailConfigurationProperties;
import com.victor.kochnev.integration.mail.exception.MailIntegrationException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@ConditionalOnProperty(name = "app.integration.mail.enabled", havingValue = "true")
@RequiredArgsConstructor
@Slf4j
public class MailClientImpl implements MailClient {
    private final JavaMailSender javaMailSender;
    private final MailConfigurationProperties mailConfigurationProperties;

    @Override
    public void sendMail(String emailTo, String message) {
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom(mailConfigurationProperties.getMailFrom());
        mailMessage.setTo(emailTo);
        mailMessage.setText(message);
        mailMessage.setSubject("Обновление ресурса");

        log.info("Send mail {}", mailMessage);
        try {
            javaMailSender.send(mailMessage);
        } catch (Exception e) {
            String msg = ExceptionUtils.getMessage(e);
            log.error("Send mail to {} with error {}", emailTo, msg);
            throw new MailIntegrationException(msg, e);
        }
        log.info("Send mail to {} succesful", emailTo);
    }
}
