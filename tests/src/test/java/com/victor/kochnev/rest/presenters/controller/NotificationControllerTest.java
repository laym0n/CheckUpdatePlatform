package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.integration.plugin.api.dto.NotificationCreateRequestBody;
import com.victor.kochnev.integration.plugin.api.dto.NotificationDto;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceDto;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceDtoBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

class NotificationControllerTest extends BaseControllerTest {
    private final String NOTIFICATION_CREATE_ENDPOINT = "/notification";
    @Autowired
    PasswordEncoder passwordEncoder;

    @Test
    void createNotification() {
        //Assign
        pluginRepository.save(PluginEntityBuilder.defaultBuilder().build());
        var requestBody = new NotificationCreateRequestBody();

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage("Msg");
        requestBody.setNotification(notificationDto);

        WebResourceDto webResourceDto = WebResourceDtoBuilder.defaultWebResourceDto();
        requestBody.setUpdatedResource(webResourceDto);

        //Action
        MvcResult mvcResult = post(NOTIFICATION_CREATE_ENDPOINT, requestBody, preparePluginHeaders());

        //Assert
        assertHttpStatusOk(mvcResult);
    }

    @Test
    void createNotification_WithoutAuthorization() {
        //Assign
        var requestBody = new NotificationCreateRequestBody();

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage("Msg");
        requestBody.setNotification(notificationDto);

        WebResourceDto webResourceDto = WebResourceDtoBuilder.defaultWebResourceDto();
        requestBody.setUpdatedResource(webResourceDto);

        //Action
        MvcResult mvcResult = post(NOTIFICATION_CREATE_ENDPOINT, requestBody, HttpHeaders.EMPTY);

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.valueOf(401));
    }

    @Test
    void createNotification_WithBadCredentials() {
        //Assign
        var requestBody = new NotificationCreateRequestBody();

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage("Msg");
        requestBody.setNotification(notificationDto);

        WebResourceDto webResourceDto = WebResourceDtoBuilder.defaultWebResourceDto();
        requestBody.setUpdatedResource(webResourceDto);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.AUTHORIZATION, "Basic bmFtZTphc2Rhc2Rhc2Rxd2Vxdw==");

        //Action
        MvcResult mvcResult = post(NOTIFICATION_CREATE_ENDPOINT, requestBody, headers);

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.valueOf(401));
    }
}
