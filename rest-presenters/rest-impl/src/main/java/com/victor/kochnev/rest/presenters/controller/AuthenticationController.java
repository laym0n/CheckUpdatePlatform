package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.AuthenticationRequest;
import com.victor.kochnev.api.dto.JwtTokenResponse;
import com.victor.kochnev.api.rest.AuthenticationApi;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.rest.presenters.security.service.JwtService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@Slf4j
public class AuthenticationController implements AuthenticationApi {
    private static final String AUTHENTICATION_ENDPOINT = "POST /authentication";
    private final AuthenticationManager authenticationManager;
    private final JwtService jwtService;

    @Override
    public ResponseEntity<JwtTokenResponse> authentication(AuthenticationRequest requestBody) {
        log.info("Request: {}", AUTHENTICATION_ENDPOINT);
        log.debug("Request: {} {}", AUTHENTICATION_ENDPOINT, requestBody);

        var authenticationToken = new UsernamePasswordAuthenticationToken(requestBody.getPrincipal(), requestBody.getCredentials());
        Authentication authentication = authenticationManager.authenticate(authenticationToken);
        UserSecurity userSecurity = (UserSecurity) authentication.getPrincipal();
        String token = jwtService.generateToken(userSecurity);

        log.info("Request: {} proccesed", AUTHENTICATION_ENDPOINT);
        return ResponseEntity.ok(new JwtTokenResponse().token(token));
    }

}
