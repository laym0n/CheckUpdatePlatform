package com.victor.kochnev.rest.presenters.security.service;

import com.victor.kochnev.core.dto.domain.entity.UserInfoDto;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.domain.enums.UserRole;
import com.victor.kochnev.rest.presenters.configuration.JwtConfigurationProperties;
import com.victor.kochnev.rest.presenters.dto.AuthenticateResponse;
import com.victor.kochnev.rest.presenters.dto.AuthenticationRefreshRequest;
import com.victor.kochnev.rest.presenters.dto.AuthenticationRequest;
import com.victor.kochnev.rest.presenters.dto.JwtTokenDto;
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
    private final JwtConfigurationProperties jwtProperties;
    private final SecurityUserService securityUserService;

    @Override
    public AuthenticateResponse authenticate(AuthenticationRequest request) {
        var authenticationToken = new UsernamePasswordAuthenticationToken(request.getLogin(), request.getPassword());
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

        UserInfoDto userInfoDto = new UserInfoDto();
        userInfoDto.setId(userSecurity.getId());
        userInfoDto.setLogin(userSecurity.getUsername());
        userInfoDto.setRoles(userSecurity.getAuthorities().stream()
                .map(authority -> UserRole.valueOf(authority.getAuthority().substring(5)))
                .toList());

        JwtTokenDto jwtTokenDto = new JwtTokenDto();
        jwtTokenDto.setAccessToken(accessToken);
        jwtTokenDto.setRefreshToken(refreshToken);
        jwtTokenDto.setAccessTokenLiveDuration(jwtProperties.getAccessTokenDuration());
        jwtTokenDto.setRefreshTokenLiveDuration(jwtProperties.getRefreshTokenDuration());
        return new AuthenticateResponse(jwtTokenDto, userInfoDto);
    }
}
