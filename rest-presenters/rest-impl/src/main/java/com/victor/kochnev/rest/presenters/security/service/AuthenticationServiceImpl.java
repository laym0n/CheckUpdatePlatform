package com.victor.kochnev.rest.presenters.security.service;

import com.victor.kochnev.api.dto.*;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Component;

import java.security.InvalidParameterException;

@Component
@RequiredArgsConstructor
public class AuthenticationServiceImpl implements AuthenticationService {
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;
    private final SecurityUserService securityUserService;

    @Override
    public AuthenticateResponse authenticate(AuthenticationRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.getEmail(), request.getPassword());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
        return getAuthenticateResponse(request.getRememberMe(), userSecurity);
    }

    @Override
    public AuthenticateResponse refresh(AuthenticationRefreshRequest request) {
        UserSecurity user = jwtService.getUserFromToken(request.getRefreshToken());
        user = (UserSecurity) securityUserService.loadUserByUsername(user.getUsername());
        return getAuthenticateResponse(request.getRememberMe(), user);
    }

    @Override
    public UserSecurity getAuthenticatedUser(String token) {
        UserSecurity user = jwtService.getUserFromToken(token);
        if (user.getAuthorities().isEmpty()) {
            throw new InvalidParameterException("Passed refresh token");
        }
        return user;
    }

    private AuthenticateResponse getAuthenticateResponse(Boolean rememberMe, UserSecurity userSecurity) {
        String accessToken = jwtService.generateAccessToken(userSecurity);
        String refreshToken = jwtService.generateRefreshToken(userSecurity, rememberMe);
        return new AuthenticateResponse()
                .jwtToken(new JwtTokenDto()
                        .accessToken(accessToken)
                        .refreshToken(refreshToken)
                )
                .user(new UserDto()
                        .id(userSecurity.getId())
                        .email(userSecurity.getUsername())
                        .roles(userSecurity.getAuthorities().stream()
                                .map(authority -> UserRoleEnum.fromValue(authority.getAuthority().substring(5)))
                                .toList()));
    }
}
