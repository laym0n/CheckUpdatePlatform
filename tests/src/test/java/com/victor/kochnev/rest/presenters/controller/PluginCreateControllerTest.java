package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.core.dto.request.AddPluginRequestDto;
import com.victor.kochnev.core.dto.response.AddPluginResponseDto;
import com.victor.kochnev.dal.entity.PluginEntity;
import com.victor.kochnev.dal.entity.PluginUsageEntity;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.domain.entity.builder.PluginDomainBuilder;
import com.victor.kochnev.domain.enums.DistributionPlanType;
import com.victor.kochnev.domain.enums.PluginStatus;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class PluginCreateControllerTest extends BaseControllerTest {
    private final String PLUGIN_ENDPOINT = "/plugin";

    @Autowired
    PasswordEncoder passwordEncoder;
    UserEntity userForRequest;

    @Test
    void successAddPlugin() {
        //Assign
        prepareDb();
        var requestBody = prepareAddRequest();

        //Action
        MvcResult mvcResult = post(PLUGIN_ENDPOINT, requestBody, prepareUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        PluginEntity pluginEntity = pluginRepository.findByName(requestBody.getName()).get();
        assertEquals(requestBody.getName(), pluginEntity.getName());
        assertEquals(requestBody.getBaseUrl(), pluginEntity.getBaseUrl());
        assertEquals(PluginStatus.CREATED, pluginEntity.getStatus());

        var responseBody = getResponseDto(mvcResult, AddPluginResponseDto.class);
        assertNotNull(responseBody);

        assertNotNull(responseBody.getAccessToken());
        assertTrue(passwordEncoder.matches(responseBody.getAccessToken(), pluginEntity.getAccessToken()));

        var pluginResponseDto = responseBody.getPlugin();
        assertEquals(pluginEntity.getId(), pluginResponseDto.getId());
        assertEquals(requestBody.getName(), pluginResponseDto.getName());
        assertEquals(requestBody.getBaseUrl(), pluginResponseDto.getBaseUrl());

        List<PluginUsageEntity> pluginUsages = pluginUsageRepository.findAll();
        assertEquals(1, pluginUsages.size());
        var pluginUsage = pluginUsages.get(0);
        assertEquals(DistributionPlanType.OWNER, pluginUsage.getDistributionMethod().getType());
        assertNull(pluginUsage.getDistributionMethod().getCost());
    }


    private AddPluginRequestDto prepareAddRequest() {
        var requestDto = new AddPluginRequestDto();
        requestDto.setBaseUrl(PluginDomainBuilder.DEFAULT_BASE_URL);
        requestDto.setName(PluginDomainBuilder.DEFAULT_NAME);
        return requestDto;
    }

    private void prepareDb() {
        userForRequest = userRepository.save(UserEntityBuilder.defaultBuilder().build());
    }
}
