package com.victor.kochnev.core.security.service;

import com.victor.kochnev.core.facade.webresourceobserving.WebResourceObservingFacade;
import com.victor.kochnev.core.security.entity.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.util.UUID;

@Component
@RequiredArgsConstructor
public class AuthorizationHelper {
    private final WebResourceObservingFacade webResourceObservingFacade;
    private final SecurityUserService securityUserService;

    public boolean checkWebResourceObservingAccess(Authentication authentication, UUID webResourceObservingId) {
        UserSecurity userSecurity = securityUserService.getUserFromAuthentication(authentication);
        return webResourceObservingFacade.checkAccess(userSecurity.getId(), webResourceObservingId);
    }
}
