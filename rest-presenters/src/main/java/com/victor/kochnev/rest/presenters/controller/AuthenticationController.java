package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.rest.presenters.api.AuthenticationApi;
import com.victor.kochnev.rest.presenters.api.dto.AuthenticationRequestBody;
import com.victor.kochnev.rest.presenters.api.dto.JwtToken;
import com.victor.kochnev.rest.presenters.utils.JwtHelper;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class AuthenticationController implements AuthenticationApi {
    private final AuthenticationManager authenticationManager;
    private final JwtHelper jwtHelper;

    @Override
    public ResponseEntity<JwtToken> authentication(AuthenticationRequestBody requestBody) {
        Authentication authenticated;

        var authenticationToken = new UsernamePasswordAuthenticationToken(requestBody.getPrincipal(), requestBody.getCredentials());
        authenticated = authenticationManager.authenticate(authenticationToken);
        String token = jwtHelper.generateToken(authenticated);
        return ResponseEntity.ok(new JwtToken().token(token));
    }

}
