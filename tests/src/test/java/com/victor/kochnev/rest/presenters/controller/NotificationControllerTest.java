package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.dal.entity.*;
import com.victor.kochnev.integration.plugin.api.dto.NotificationCreateRequestBody;
import com.victor.kochnev.integration.plugin.api.dto.NotificationDto;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceDto;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceDtoBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class NotificationControllerTest extends BaseControllerTest {
    private final String NOTIFICATION_CREATE_ENDPOINT = "/notification";
    private final String WEBRESOURCE_NAME = "webResourceName1";
    private UUID USER_ID1;
    private UUID USER_ID2;
    private UUID USER_ID3;
    private UUID USER_ID4;

    @Test
    void createNotification() {
        //Assign
        prepareDb();
        var requestBody = new NotificationCreateRequestBody();

        NotificationDto notificationDto = new NotificationDto();
        notificationDto.setMessage("Msg");
        requestBody.setNotification(notificationDto);

        WebResourceDto webResourceDto = WebResourceDtoBuilder.defaultWebResourceDto()
                .name(WEBRESOURCE_NAME);
        requestBody.setUpdatedResource(webResourceDto);

        //Action
        MvcResult mvcResult = post(NOTIFICATION_CREATE_ENDPOINT, requestBody, preparePluginHeaders());

        //Assert
        assertHttpStatusOk(mvcResult);

        List<NotificationEntity> notificationList = notificationRepository.findAll();
        assertNotNull(notificationList);
        assertEquals(3, notificationList.size());
        assertTrue(notificationList.stream().anyMatch(i -> i.getUser().getId().equals(USER_ID1)));
        assertTrue(notificationList.stream().anyMatch(i -> i.getUser().getId().equals(USER_ID3)));
        assertTrue(notificationList.stream().anyMatch(i -> i.getUser().getId().equals(USER_ID4)));
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

    void prepareDb() {
        USER_ID1 = userRepository.save(UserEntityBuilder.postfixBuilder(1).build()).getId();
        USER_ID2 = userRepository.save(UserEntityBuilder.postfixBuilder(2).build()).getId();
        USER_ID3 = userRepository.save(UserEntityBuilder.postfixBuilder(3).build()).getId();
        USER_ID4 = userRepository.save(UserEntityBuilder.postfixBuilder(4).build()).getId();
        UUID ownerUserId = userRepository.save(UserEntityBuilder.postfixBuilder(5).build()).getId();

        UUID pluginId1 = pluginRepository.save(PluginEntityBuilder.persistedDefaultBuilder()
                .ownerUser(userRepository.findById(ownerUserId).get()).build()).getId();
        UUID pluginId2 = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(2)
                .ownerUser(userRepository.findById(ownerUserId).get()).build()).getId();

        UUID webResourceId1 = webResourceRepository.save(WebResourceEntityBuilder.postfixBuilder(1)
                .name(WEBRESOURCE_NAME)
                .plugin(pluginRepository.findById(pluginId1).get()).build()).getId();
        UUID webResourceId2 = webResourceRepository.save(WebResourceEntityBuilder.postfixBuilder(2)
                .plugin(pluginRepository.findById(pluginId2).get()).build()).getId();

        UUID puId1 = pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
                .user(userRepository.findById(USER_ID1).get())
                .plugin(pluginRepository.findById(pluginId1).get())
                .expiredDate(null)
                .build()).getId();
        UUID puId2 = pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(2)
                .user(userRepository.findById(USER_ID2).get())
                .plugin(pluginRepository.findById(pluginId1).get())
                .expiredDate(ZonedDateTime.now().minusMinutes(5))
                .build()).getId();
        UUID puId3 = pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(3)
                .user(userRepository.findById(USER_ID3).get())
                .plugin(pluginRepository.findById(pluginId1).get())
                .expiredDate(ZonedDateTime.now().plusMinutes(5))
                .build()).getId();
        UUID puId4 = pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(4)
                .user(userRepository.findById(USER_ID4).get())
                .plugin(pluginRepository.findById(pluginId1).get())
                .expiredDate(ZonedDateTime.now().plusDays(5))
                .build()).getId();

        UUID puId5 = pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(5)
                .user(userRepository.findById(USER_ID1).get())
                .plugin(pluginRepository.findById(pluginId2).get())
                .expiredDate(null)
                .build()).getId();
        UUID puId6 = pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(6)
                .user(userRepository.findById(USER_ID2).get())
                .plugin(pluginRepository.findById(pluginId2).get())
                .expiredDate(ZonedDateTime.now().plusMinutes(5))
                .build()).getId();
        UUID puId7 = pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(7)
                .user(userRepository.findById(USER_ID3).get())
                .plugin(pluginRepository.findById(pluginId2).get())
                .expiredDate(ZonedDateTime.now().minusMinutes(5))
                .build()).getId();
        UUID puId8 = pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(8)
                .user(userRepository.findById(USER_ID4).get())
                .plugin(pluginRepository.findById(pluginId2).get())
                .expiredDate(ZonedDateTime.now().plusDays(5))
                .build()).getId();

        UUID observingId1 = observingRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(1)
                .user(userRepository.findById(USER_ID1).get())
                .webResource(webResourceRepository.findById(webResourceId1).get())
                .build()).getId();
        UUID observingId2 = observingRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(2)
                .user(userRepository.findById(USER_ID2).get())
                .webResource(webResourceRepository.findById(webResourceId1).get())
                .build()).getId();
        UUID observingId3 = observingRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(3)
                .user(userRepository.findById(USER_ID3).get())
                .webResource(webResourceRepository.findById(webResourceId1).get())
                .build()).getId();
        UUID observingId4 = observingRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(4)
                .user(userRepository.findById(USER_ID4).get())
                .webResource(webResourceRepository.findById(webResourceId1).get())
                .build()).getId();

        UUID observingId5 = observingRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(5)
                .user(userRepository.findById(USER_ID1).get())
                .webResource(webResourceRepository.findById(webResourceId2).get())
                .build()).getId();
        UUID observingId6 = observingRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(6)
                .user(userRepository.findById(USER_ID2).get())
                .webResource(webResourceRepository.findById(webResourceId2).get())
                .build()).getId();
        UUID observingId7 = observingRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(7)
                .user(userRepository.findById(USER_ID3).get())
                .webResource(webResourceRepository.findById(webResourceId2).get())
                .build()).getId();
        UUID observingId8 = observingRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(8)
                .user(userRepository.findById(USER_ID4).get())
                .webResource(webResourceRepository.findById(webResourceId2).get())
                .build()).getId();
    }
}
