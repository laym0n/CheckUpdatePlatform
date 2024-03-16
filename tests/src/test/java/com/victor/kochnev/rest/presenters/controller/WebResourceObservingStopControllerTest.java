package com.victor.kochnev.rest.presenters.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.api.dto.ObserveSettings;
import com.victor.kochnev.api.dto.ObserveStatusEnum;
import com.victor.kochnev.api.dto.WebResource;
import com.victor.kochnev.api.dto.WebResourceObserving;
import com.victor.kochnev.dal.entity.*;
import com.victor.kochnev.domain.enums.ObserveStatus;
import com.victor.kochnev.integration.plugin.api.dto.CanObserveResponseBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.time.ZonedDateTime;
import java.util.UUID;

import static com.github.tomakehurst.wiremock.client.WireMock.deleteRequestedFor;
import static com.github.tomakehurst.wiremock.client.WireMock.urlEqualTo;
import static org.junit.jupiter.api.Assertions.*;

class WebResourceObservingStopControllerTest extends BaseControllerTest {
    private final String WEBRESOURCE_OBSERVING_STOP_ENDPOINT = "/webresource/observing/%s/stop";
    private final String PLUGIN_WEBRESOURCE_ENDPOINT = "/webresource";
    private UUID USER_ID;
    private UUID PLUGIN_ID;
    private UUID WEBRESOURCE_ID;
    private UUID WEBRESOURCE_OBSERVING_ID;

    @Test
    void removeWebResourceObserving_WhenUserAndPluginExists() {
        //Assign
        prepareDb();
        stubSuccessWebResourceRemove();
        String url = String.format(WEBRESOURCE_OBSERVING_STOP_ENDPOINT, WEBRESOURCE_OBSERVING_ID);

        //Action
        MvcResult mvcResult = put(url, null, prepareUserHeaders());

        //Assert
        assertHttpStatusOk(mvcResult);

        var optionalWebResource = webResourceRepository.findById(WEBRESOURCE_ID);
        assertTrue(optionalWebResource.isPresent());
        WebResourceEntity webResourceEntity = optionalWebResource.get();
        assertEquals(ObserveStatus.NOT_OBSERVE, webResourceEntity.getStatus());

        var optionalObservingEntity = observingRepository.findByWebResourceIdAndUserId(webResourceEntity.getId(), USER_ID);
        var observingEntity = optionalObservingEntity.get();
        assertEquals(ObserveStatus.NOT_OBSERVE, observingEntity.getStatus());

        WebResourceObserving responseDto = getResponseDto(mvcResult, WebResourceObserving.class);
        assertNotNull(responseDto);

        assertResponse(responseDto, webResourceEntity);

        wireMockServer.verify(1, deleteRequestedFor(urlEqualTo(PLUGIN_WEBRESOURCE_ENDPOINT)));
    }

    @Test
    void removeWebResourceObserving_WhenMultipleObservings_ExpectWebResourceObserve() {
        //Assign
        prepareDb();
        UUID userId1 = userRepository.save(UserEntityBuilder.persistedPostfixBuilder(1).build()).getId();
        pluginUsageRepository.save(pluginUsageRepository.save(PluginUsageEntityBuilder.persistedDefaultBuilder()
                .user(userRepository.findById(userId1).get())
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .expiredDate(ZonedDateTime.now().plusMinutes(5)).build()));
        observingRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userRepository.findById(userId1).get())
                .webResource(webResourceRepository.findById(WEBRESOURCE_ID).get())
                .status(ObserveStatus.OBSERVE)
                .build()).getId();
        String url = String.format(WEBRESOURCE_OBSERVING_STOP_ENDPOINT, WEBRESOURCE_OBSERVING_ID);

        //Action
        MvcResult mvcResult = put(url, null, prepareUserHeaders());

        //Assert
        assertHttpStatusOk(mvcResult);

        var optionalWebResource = webResourceRepository.findById(WEBRESOURCE_ID);
        assertTrue(optionalWebResource.isPresent());
        WebResourceEntity webResourceEntity = optionalWebResource.get();
        assertEquals(ObserveStatus.OBSERVE, webResourceEntity.getStatus());

        var optionalObservingEntity = observingRepository.findByWebResourceIdAndUserId(webResourceEntity.getId(), USER_ID);
        var observingEntity = optionalObservingEntity.get();
        assertEquals(ObserveStatus.NOT_OBSERVE, observingEntity.getStatus());

        WebResourceObserving responseDto = getResponseDto(mvcResult, WebResourceObserving.class);
        assertNotNull(responseDto);

        assertResponse(responseDto, webResourceEntity);
    }

    @Test
    void removeWebResourceObserving_WhenMultipleObservings_ExpectWebResourceNotObserve() {
        //Assign
        prepareDb();
        stubSuccessWebResourceRemove();
        UUID userId1 = userRepository.save(UserEntityBuilder.persistedPostfixBuilder(1).build()).getId();
        pluginUsageRepository.save(pluginUsageRepository.save(PluginUsageEntityBuilder.persistedDefaultBuilder()
                .user(userRepository.findById(userId1).get())
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .expiredDate(ZonedDateTime.now().plusMinutes(5)).build()));
        observingRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userRepository.findById(userId1).get())
                .webResource(webResourceRepository.findById(WEBRESOURCE_ID).get())
                .status(ObserveStatus.NOT_OBSERVE)
                .build()).getId();
        String url = String.format(WEBRESOURCE_OBSERVING_STOP_ENDPOINT, WEBRESOURCE_OBSERVING_ID);

        //Action
        MvcResult mvcResult = put(url, null, prepareUserHeaders());

        //Assert
        assertHttpStatusOk(mvcResult);

        var optionalWebResource = webResourceRepository.findById(WEBRESOURCE_ID);
        assertTrue(optionalWebResource.isPresent());
        WebResourceEntity webResourceEntity = optionalWebResource.get();
        assertEquals(ObserveStatus.NOT_OBSERVE, webResourceEntity.getStatus());

        var optionalObservingEntity = observingRepository.findByWebResourceIdAndUserId(webResourceEntity.getId(), USER_ID);
        var observingEntity = optionalObservingEntity.get();
        assertEquals(ObserveStatus.NOT_OBSERVE, observingEntity.getStatus());

        WebResourceObserving responseDto = getResponseDto(mvcResult, WebResourceObserving.class);
        assertNotNull(responseDto);

        assertResponse(responseDto, webResourceEntity);
        wireMockServer.verify(1, deleteRequestedFor(urlEqualTo(PLUGIN_WEBRESOURCE_ENDPOINT)));
    }

    @Test
    void removeWebResourceObserving_WhenRemoveNotPermittedObserving() {
        //Assign
        prepareDb();
        UUID userId1 = userRepository.save(UserEntityBuilder.persistedPostfixBuilder(1).build()).getId();
        pluginUsageRepository.save(pluginUsageRepository.save(PluginUsageEntityBuilder.persistedDefaultBuilder()
                .user(userRepository.findById(userId1).get())
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .expiredDate(ZonedDateTime.now().plusMinutes(5)).build()));
        UUID notPermittedObservingId = observingRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userRepository.findById(userId1).get())
                .webResource(webResourceRepository.findById(WEBRESOURCE_ID).get())
                .status(ObserveStatus.OBSERVE)
                .build()).getId();
        String url = String.format(WEBRESOURCE_OBSERVING_STOP_ENDPOINT, notPermittedObservingId);

        //Action
        MvcResult mvcResult = put(url, null, prepareUserHeaders());

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.FORBIDDEN);

        var optionalWebResource = webResourceRepository.findById(WEBRESOURCE_ID);
        assertTrue(optionalWebResource.isPresent());
        WebResourceEntity webResourceEntity = optionalWebResource.get();
        assertEquals(ObserveStatus.OBSERVE, webResourceEntity.getStatus());

        var optionalObservingEntity = observingRepository.findByWebResourceIdAndUserId(WEBRESOURCE_ID, userId1);
        assertTrue(optionalObservingEntity.isPresent());
        WebResourceObservingEntity observingEntity = optionalObservingEntity.get();
        assertEquals(ObserveStatus.OBSERVE, observingEntity.getStatus());
    }

    private void assertResponse(WebResourceObserving responseDto, WebResourceEntity webResourceEntity) {
        WebResource webResourceDto = responseDto.getWebResourceDto();
        assertNotNull(webResourceDto);
        assertEquals(webResourceEntity.getId(), webResourceDto.getId());
        assertEquals(CanObserveResponseBuilder.DEFAULT_WEB_RESOURCE.getName(), webResourceDto.getName());
        assertEquals(CanObserveResponseBuilder.DEFAULT_WEB_RESOURCE.getDescription(), webResourceDto.getDescription());

        ObserveSettings observeSettings = responseDto.getObserveSettings();
        assertEquals(ObserveStatusEnum.NOT_OBSERVE, responseDto.getStatus());
        assertNotNull(observeSettings);
        assertEquals(true, observeSettings.getNeedNotify());
    }

    private void stubSuccessWebResourceRemove() {
        wireMockServer.stubFor(WireMock.delete(PLUGIN_WEBRESOURCE_ENDPOINT)
                .willReturn(WireMock.aResponse().withStatus(200)));
    }

    private void prepareDb() {
        USER_ID = userRepository.save(UserEntityBuilder.defaultBuilder().build()).getId();
        PLUGIN_ID = pluginRepository.save(PluginEntityBuilder.persistedDefaultBuilder()
                .ownerUser(userRepository.findById(USER_ID).get()).build()).getId();
        pluginUsageRepository.save(PluginUsageEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .user(userRepository.findById(USER_ID).get()).build());
        WEBRESOURCE_ID = webResourceRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .status(ObserveStatus.OBSERVE)
                .build()).getId();
        WEBRESOURCE_OBSERVING_ID = observingRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userRepository.findById(USER_ID).get())
                .webResource(webResourceRepository.findById(WEBRESOURCE_ID).get())
                .status(ObserveStatus.OBSERVE)
                .build()).getId();
    }
}
