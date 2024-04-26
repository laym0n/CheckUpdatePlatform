package com.victor.kochnev.rest.presenters.security.service;

import com.victor.kochnev.BaseRestPresentersTest;
import com.victor.kochnev.core.security.entity.UserAuthoritySecurity;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.domain.enums.UserRole;
import com.victor.kochnev.rest.presenters.configuration.JwtConfigurationProperties;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class JwtServiceTest extends BaseRestPresentersTest {
    @InjectMocks
    JwtService jwtService;
    @Spy
    JwtConfigurationProperties properties = new JwtConfigurationProperties();

    @BeforeEach
    void init() {
        properties.setSecret("SecretSecretSecretSecretSecretSecretSecretSecretSecretSecretSecretSecretSecretSecretSecretSecretSecret");
        properties.setAccessTokenDuration(Duration.of(5, ChronoUnit.MINUTES));
        properties.setRefreshTokenDuration(Duration.of(5, ChronoUnit.MINUTES));
        properties.setRefreshTokenRememberMeDuration(Duration.of(5, ChronoUnit.MINUTES));
    }

    @Test
    void testGenerateAccessToken() {
        //Assign
        UserSecurity userSecurity = new UserSecurity(UUID.randomUUID(),
                "email",
                "password",
                true,
                List.of(new UserAuthoritySecurity("authority")),
                List.of(UserRole.EMPLOYEE));

        //Action
        String token = jwtService.generateAccessToken(userSecurity);

        //Assert
        assertNotNull(token);
        UserSecurity parsedUser = jwtService.getUserFromToken(token);
        assertEquals(userSecurity.getId(), parsedUser.getId());
        assertEquals(userSecurity.getUsername(), parsedUser.getUsername());
        assertEquals("", parsedUser.getPassword());
        assertEquals(userSecurity.getAuthorities(), parsedUser.getAuthorities());
        assertEquals(userSecurity.isEnabled(), parsedUser.isEnabled());
        assertEquals(userSecurity.getRoles(), parsedUser.getRoles());
    }

    @Test
    void testGenerateRefreshToken() {
        //Assign
        UserSecurity userSecurity = new UserSecurity(UUID.randomUUID(),
                "email",
                "password",
                true,
                List.of(new UserAuthoritySecurity("authority")),
                List.of(UserRole.EMPLOYEE));

        //Action
        String token = jwtService.generateRefreshToken(userSecurity, true);

        //Assert
        assertNotNull(token);
        UserSecurity parsedUser = jwtService.getUserFromToken(token);
        assertEquals(userSecurity.getId(), parsedUser.getId());
        assertEquals(userSecurity.getUsername(), parsedUser.getUsername());
        assertEquals("", parsedUser.getPassword());
        assertEquals(0, parsedUser.getAuthorities().size());
        assertEquals(0, parsedUser.getRoles().size());
    }
}
