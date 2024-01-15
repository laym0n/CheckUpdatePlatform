package com.victor.kochnev.rest.presenters;

import lombok.extern.slf4j.Slf4j;
import org.springframework.context.event.EventListener;
import org.springframework.security.authentication.event.AbstractAuthenticationFailureEvent;
import org.springframework.security.authentication.event.AuthenticationSuccessEvent;
import org.springframework.stereotype.Component;

@Component
@Slf4j
public class AuthenticationEvents {
    @EventListener
    public void onSuccess(AuthenticationSuccessEvent success) {
        log.info("{}", success);
    }

    @EventListener
    public void onFailure(AbstractAuthenticationFailureEvent failures) {
        log.info("{}", failures);
    }
}
