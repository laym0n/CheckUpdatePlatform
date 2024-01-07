package com.victor.kochnev.controllers;

import com.victor.kochnev.api.SignApi;
import com.victor.kochnev.dto.UserRegistrationRequestDto;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class UserController implements SignApi {
    @Override
    public ResponseEntity<Void> register(UserRegistrationRequestDto requestDto) {
        return ResponseEntity.internalServerError().build();
    }
}
