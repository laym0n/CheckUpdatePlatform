package com.victor.kochnev.rest.presenters.security.service;

import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.rest.presenters.dto.AuthenticateResponse;
import com.victor.kochnev.rest.presenters.dto.AuthenticationRefreshRequest;
import com.victor.kochnev.rest.presenters.dto.AuthenticationRequest;

public interface AuthenticationService {
    AuthenticateResponse authenticate(AuthenticationRequest request);

    AuthenticateResponse refresh(AuthenticationRefreshRequest request);

    UserSecurity getAuthenticatedUser(String token);
}
