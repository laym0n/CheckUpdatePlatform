package com.victor.kochnev.rest.presenters.security.service;

import com.victor.kochnev.rest.presenters.security.entity.UserSecurity;
import org.springframework.security.core.Authentication;

public interface SecurityUserService {
    UserSecurity getCurrentUser();

    UserSecurity getUserFromAuthentication(Authentication authentication);
}
