package com.victor.kochnev.rest.presenters.security.service;

import com.victor.kochnev.api.dto.AuthenticateResponse;
import com.victor.kochnev.api.dto.AuthenticationRefreshRequest;
import com.victor.kochnev.api.dto.AuthenticationRequest;
import com.victor.kochnev.core.security.entity.UserSecurity;

public interface AuthenticationService {
    AuthenticateResponse authenticate(AuthenticationRequest request);

    AuthenticateResponse refresh(AuthenticationRefreshRequest request);

    UserSecurity getAuthenticatedUser(String token);
}
