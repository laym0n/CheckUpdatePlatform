package com.victor.kochnev.core.integration;

public interface MailClient {
    void sendMail(String emailTo, String message);
}
