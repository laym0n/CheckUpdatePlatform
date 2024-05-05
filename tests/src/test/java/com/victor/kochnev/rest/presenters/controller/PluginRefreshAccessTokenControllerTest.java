package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.core.dto.response.RefreshTokenResponseDto;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PluginRefreshAccessTokenControllerTest extends BaseControllerTest {
    private final String REFRESH_ACCESS_TOKEN_ENDPOINT = "/plugin/%s/refresh_token";
    @Autowired
    PasswordEncoder passwordEncoder;
    UserEntity userForRequest;
    private UUID PLUGIN_ID;

    @Test
    void successRefresh() {
        //Assign
        prepareDb();

        String url = String.format(REFRESH_ACCESS_TOKEN_ENDPOINT, PLUGIN_ID);

        //Action
        MvcResult mvcResult = post(url, null, prepareUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        var responseBody = getResponseDto(mvcResult, RefreshTokenResponseDto.class);
        assertNotNull(responseBody);
        String accessToken = responseBody.getAccessToken();
        assertNotNull(accessToken);

        PluginEntity pluginEntity = pluginRepository.findById(PLUGIN_ID).get();
        assertTrue(passwordEncoder.matches(accessToken, pluginEntity.getAccessToken()));
    }

    @Test
    void refreshByNotOwner_Expect401() {
        //Assign
        prepareDb();
        userForRequest = userRepository.save(UserEntityBuilder.persistedPostfixBuilder(1).build());
        String initAccessToken = pluginRepository.findById(PLUGIN_ID).get().getAccessToken();

        String url = String.format(REFRESH_ACCESS_TOKEN_ENDPOINT, PLUGIN_ID);

        //Action
        MvcResult mvcResult = post(url, null, prepareUserHeaders(userForRequest));

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.UNAUTHORIZED);

        PluginEntity pluginEntity = pluginRepository.findById(PLUGIN_ID).get();
        assertEquals(initAccessToken, pluginEntity.getAccessToken());
    }

    private void prepareDb() {
        userForRequest = userRepository.save(UserEntityBuilder.defaultBuilder().build());
        PLUGIN_ID = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .ownerUser(userRepository.findById(userForRequest.getId()).get())
                .build()).getId();
    }
}
