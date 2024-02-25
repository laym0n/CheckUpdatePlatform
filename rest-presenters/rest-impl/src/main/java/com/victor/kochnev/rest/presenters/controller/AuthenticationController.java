package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.api.dto.AuthenticationRequestBody;
import com.victor.kochnev.api.dto.JwtToken;
import com.victor.kochnev.api.rest.AuthenticationApi;
import com.victor.kochnev.rest.presenters.security.entity.UserSecurity;
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
    public ResponseEntity<JwtToken> authentication(AuthenticationRequestBody requestBody) {
        log.info("Request: {}", AUTHENTICATION_ENDPOINT);
        log.debug("Request: {} {}", AUTHENTICATION_ENDPOINT, requestBody);

        var authenticationToken = new UsernamePasswordAuthenticationToken(requestBody.getPrincipal(), requestBody.getCredentials());
        Authentication authenticated = authenticationManager.authenticate(authenticationToken);
        UserSecurity userSecurity = (UserSecurity) authenticated.getPrincipal();
        String token = jwtService.generateToken(userSecurity);

        log.info("Request: {} proccesed", AUTHENTICATION_ENDPOINT);
        return ResponseEntity.ok(new JwtToken().token(token));
    }

}
