package com.victor.kochnev.integration.mail.client;

import com.victor.kochnev.core.integration.MailClient;
import com.victor.kochnev.integration.mail.config.MailConfigurationProperties;
import lombok.RequiredArgsConstructor;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
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

        javaMailSender.send(mailMessage);
    }
}
