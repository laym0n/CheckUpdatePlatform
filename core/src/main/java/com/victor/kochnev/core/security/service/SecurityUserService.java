package com.victor.kochnev.core.security.service;

import com.victor.kochnev.core.security.entity.UserSecurity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.userdetails.UserDetailsService;

public interface SecurityUserService extends UserDetailsService {
    UserSecurity getCurrentUser();

    UserSecurity getUserFromAuthentication(Authentication authentication);
}
