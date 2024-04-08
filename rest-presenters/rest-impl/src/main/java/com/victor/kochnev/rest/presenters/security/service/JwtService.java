package com.victor.kochnev.rest.presenters.security.service;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.victor.kochnev.core.security.entity.UserAuthoritySecurity;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.rest.presenters.configuration.JwtConfigurationProperties;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import org.springframework.stereotype.Component;

import java.time.Duration;
import java.util.*;

@Component
@RequiredArgsConstructor
public class JwtService {
    private static final ObjectMapper objectMapper = new ObjectMapper();
    private final JwtConfigurationProperties jwtProperties;

    @SneakyThrows
    public UserSecurity getUserFromToken(String token) {
        Map<String, Object> mappedUser = Jwts.parser()
                .setSigningKey(jwtProperties.getSecret())
                .build()
                .parseClaimsJws(token)
                .getPayload();
        UUID id = UUID.fromString((String) mappedUser.get("id"));
        String username = (String) mappedUser.get("username");
        UserSecurity userSecurity;
        if (mappedUser.containsKey("authorities")) {
            boolean enabled = (boolean) mappedUser.get("enabled");
            List<UserAuthoritySecurity> authorities = objectMapper.convertValue(mappedUser.get("authorities"), new TypeReference<List<UserAuthoritySecurity>>() {
            });
            userSecurity = new UserSecurity(id, username, "", enabled, authorities);
        } else {
            userSecurity = new UserSecurity(id, username);
        }
        return userSecurity;
    }

    public String generateRefreshToken(UserSecurity userSecurity, boolean isRememberMeSet) {
        Map<String, Object> userMap = new HashMap<>();
        userMap.put("id", userSecurity.getId());
        userMap.put("username", userSecurity.getUsername());
        Duration lifeTime = isRememberMeSet ? jwtProperties.getRefreshTokenRemeberMeDuration() : jwtProperties.getRefreshTokenDuration();
        return doGenerateToken(userMap, userSecurity.getUsername(), lifeTime);
    }

    @SneakyThrows
    public String generateAccessToken(UserSecurity userSecurity) {
        HashMap<String, Object> userMap = objectMapper.convertValue(userSecurity, new TypeReference<>() {
        });
        userMap.remove("password");
        return doGenerateToken(userMap, userSecurity.getUsername(), jwtProperties.getAccessTokenDuration());
    }

    private String doGenerateToken(Map<String, Object> claims, String subject, Duration lifeTime) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + lifeTime.toMillis());
        return Jwts.builder()
                .claims(claims)
                .subject(subject)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(SignatureAlgorithm.HS512, jwtProperties.getSecret())
                .compact();
    }
}
