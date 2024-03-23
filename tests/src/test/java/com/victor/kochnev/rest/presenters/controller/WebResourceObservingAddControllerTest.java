package com.victor.kochnev.rest.presenters.controller;

import com.github.tomakehurst.wiremock.client.WireMock;
import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.api.dto.*;
import com.victor.kochnev.dal.embeddable.object.EmbeddableDistributionMethodBuilder;
import com.victor.kochnev.dal.entity.*;
import com.victor.kochnev.domain.enums.ObserveStatus;
import com.victor.kochnev.domain.enums.PluginStatus;
import com.victor.kochnev.integration.plugin.api.dto.CanObserveResponse;
import com.victor.kochnev.integration.plugin.api.dto.CanObserveResponseBuilder;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceDto;
import com.victor.kochnev.integration.plugin.api.dto.WebResourceDtoBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.time.ZonedDateTime;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WebResourceObservingAddControllerTest extends BaseControllerTest {
    private final String WEBRESOURCE_OBSERVING_ENDPOINT = "/webresource/observing";
    private final String PLUGIN_WEBRESOURCE_ENDPOINT = "/webresource";
    private final String PLUGIN_CAN_OBSERVE_ENDPOINT = "/webresource/can/observe";
    private UUID USER_ID;
    private UUID PLUGIN_ID;

    @Test
    void addWebResourceObserving_WhenUserAndPluginExists() {
        //Assign
        prepareDb();
        WebResourceObservingAddRequestBody requestBody = prepareAddRequest();

        String expectedName = WebResourceDtoBuilder.DEFAULT_NAME;
        stubSuccessCanObserve(expectedName);
        stubSuccessWebResourceAdd(expectedName);

        //Action
        MvcResult mvcResult = post(WEBRESOURCE_OBSERVING_ENDPOINT, requestBody, prepareSimpleUserHeaders());

        //Assert
        assertHttpStatusOk(mvcResult);

        var optionalWebResource = webResourceRepository.findByName(CanObserveResponseBuilder.DEFAULT_WEB_RESOURCE.getName());
        assertTrue(optionalWebResource.isPresent());
        WebResourceEntity webResourceEntity = optionalWebResource.get();
        assertWebResourceEntity(webResourceEntity);

        var optionalObservingEntity = observingRepository.findByWebResourceIdAndUserId(webResourceEntity.getId(), USER_ID);
        var observingEntity = optionalObservingEntity.get();
        assertWebResourceObservingEntity(observingEntity);

        WebResourceObserving responseDto = getResponseDto(mvcResult, WebResourceObserving.class);
        assertNotNull(responseDto);

        assertResponse(responseDto, webResourceEntity);
    }

    @Test
    void addWebResourceObserving_PluginUsageNotPermited_Expect403() {
        //Assign
        prepareDb();
        pluginUsageRepository.deleteAll();
        WebResourceObservingAddRequestBody requestBody = prepareAddRequest();

        String expectedName = WebResourceDtoBuilder.DEFAULT_NAME;
        stubSuccessCanObserve(expectedName);
        stubSuccessWebResourceAdd(expectedName);

        //Action
        MvcResult mvcResult = post(WEBRESOURCE_OBSERVING_ENDPOINT, requestBody, prepareSimpleUserHeaders());

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.UNAUTHORIZED);
    }

    @Test
    void addWebResourceObserving_PluginUsageNotPermited1_Expect403() {
        //Assign
        prepareDb();
        pluginUsageRepository.deleteAll();
        pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .user(userRepository.findById(USER_ID).get())
                .expiredDate(ZonedDateTime.now().minusMinutes(5))
                .distributionMethod(EmbeddableDistributionMethodBuilder.defaultSubscribeDistribution().build())
                .build());
        WebResourceObservingAddRequestBody requestBody = prepareAddRequest();

        String expectedName = WebResourceDtoBuilder.DEFAULT_NAME;
        stubSuccessCanObserve(expectedName);
        stubSuccessWebResourceAdd(expectedName);

        //Action
        MvcResult mvcResult = post(WEBRESOURCE_OBSERVING_ENDPOINT, requestBody, prepareSimpleUserHeaders());

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.UNAUTHORIZED);
    }

    @Test
    void addWebResourceObserving_WhenUserPluginAndWebResourceExists() {
        //Assign
        prepareDb();
        UUID webResourceId = webResourceRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .status(ObserveStatus.NOT_OBSERVE)
                .build()).getId();
        WebResourceObservingAddRequestBody requestBody = prepareAddRequest();

        stubSuccessCanObserve(WebResourceEntityBuilder.DEFAULT_NAME);
        stubSuccessWebResourceAdd(WebResourceEntityBuilder.DEFAULT_NAME);

        //Action
        MvcResult mvcResult = post(WEBRESOURCE_OBSERVING_ENDPOINT, requestBody, prepareSimpleUserHeaders());

        //Assert
        assertHttpStatusOk(mvcResult);

        var optionalWebResource = webResourceRepository.findByName(CanObserveResponseBuilder.DEFAULT_WEB_RESOURCE.getName());
        assertTrue(optionalWebResource.isPresent());
        WebResourceEntity webResourceEntity = optionalWebResource.get();
        assertEquals(webResourceId, webResourceEntity.getId());
        assertWebResourceEntity(webResourceEntity);

        var optionalObservingEntity = observingRepository.findByWebResourceIdAndUserId(webResourceId, USER_ID);
        var observingEntity = optionalObservingEntity.get();
        assertWebResourceObservingEntity(observingEntity);

        WebResourceObserving responseDto = getResponseDto(mvcResult, WebResourceObserving.class);
        assertNotNull(responseDto);

        assertResponse(responseDto, webResourceEntity);
    }

    @Test
    void addWebResourceObserving_WhenUserPluginWebResourceAndWebResourceObservingExists() {
        //Assign
        prepareDb();
        UUID webResourceId = webResourceRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .status(ObserveStatus.NOT_OBSERVE)
                .build()).getId();
        UUID observingId = observingRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .webResource(webResourceRepository.findById(webResourceId).get())
                .user(userRepository.findById(USER_ID).get())
                .status(ObserveStatus.NOT_OBSERVE)
                .build()).getId();
        WebResourceObservingAddRequestBody requestBody = prepareAddRequest();

        stubSuccessCanObserve(WebResourceEntityBuilder.DEFAULT_NAME);
        stubSuccessWebResourceAdd(WebResourceEntityBuilder.DEFAULT_NAME);

        //Action
        MvcResult mvcResult = post(WEBRESOURCE_OBSERVING_ENDPOINT, requestBody, prepareSimpleUserHeaders());

        //Assert
        assertHttpStatusOk(mvcResult);

        var optionalWebResource = webResourceRepository.findByName(CanObserveResponseBuilder.DEFAULT_WEB_RESOURCE.getName());
        assertTrue(optionalWebResource.isPresent());
        WebResourceEntity webResourceEntity = optionalWebResource.get();
        assertEquals(webResourceId, webResourceEntity.getId());
        assertWebResourceEntity(webResourceEntity);

        var optionalObservingEntity = observingRepository.findByWebResourceIdAndUserId(webResourceId, USER_ID);
        var observingEntity = optionalObservingEntity.get();
        assertEquals(observingId, observingEntity.getId());
        assertWebResourceObservingEntity(observingEntity);

        WebResourceObserving responseDto = getResponseDto(mvcResult, WebResourceObserving.class);
        assertNotNull(responseDto);

        assertResponse(responseDto, webResourceEntity);
    }

    @Test
    void addWebResourceObserving_WhenWebResourceObservedExists() {
        //Assign
        prepareDb();
        UUID webResourceId = webResourceRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .status(ObserveStatus.OBSERVE)
                .build()).getId();
        UUID observingId = observingRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .webResource(webResourceRepository.findById(webResourceId).get())
                .user(userRepository.findById(USER_ID).get())
                .status(ObserveStatus.OBSERVE)
                .build()).getId();
        WebResourceObservingAddRequestBody requestBody = prepareAddRequest();

        stubSuccessCanObserve(WebResourceEntityBuilder.DEFAULT_NAME);

        //Action
        MvcResult mvcResult = post(WEBRESOURCE_OBSERVING_ENDPOINT, requestBody, prepareSimpleUserHeaders());

        //Assert
        assertHttpStatusOk(mvcResult);

        var optionalWebResource = webResourceRepository.findByName(CanObserveResponseBuilder.DEFAULT_WEB_RESOURCE.getName());
        assertTrue(optionalWebResource.isPresent());
        WebResourceEntity webResourceEntity = optionalWebResource.get();
        assertEquals(webResourceId, webResourceEntity.getId());
        assertWebResourceEntity(webResourceEntity);

        var optionalObservingEntity = observingRepository.findByWebResourceIdAndUserId(webResourceId, USER_ID);
        var observingEntity = optionalObservingEntity.get();
        assertEquals(observingId, observingEntity.getId());
        assertWebResourceObservingEntity(observingEntity);

        WebResourceObserving responseDto = getResponseDto(mvcResult, WebResourceObserving.class);
        assertNotNull(responseDto);

        assertResponse(responseDto, webResourceEntity);
    }

    @Test
    void addWebResourceObserving_WhenWebResourceObservedByOtherUserExists() {
        //Assign
        prepareDb();
        UUID userId1 = userRepository.save(UserEntityBuilder.persistedPostfixBuilder(1).build()).getId();
        pluginUsageRepository.save(PluginUsageEntityBuilder.persistedDefaultBuilder()
                .user(userRepository.findById(userId1).get())
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .build());
        UUID webResourceId = webResourceRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .status(ObserveStatus.OBSERVE)
                .build()).getId();
        observingRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .webResource(webResourceRepository.findById(webResourceId).get())
                .user(userRepository.findById(userId1).get())
                .status(ObserveStatus.OBSERVE)
                .build()).getId();
        WebResourceObservingAddRequestBody requestBody = prepareAddRequest();

        stubSuccessCanObserve(WebResourceEntityBuilder.DEFAULT_NAME);

        //Action
        MvcResult mvcResult = post(WEBRESOURCE_OBSERVING_ENDPOINT, requestBody, prepareSimpleUserHeaders());

        //Assert
        assertHttpStatusOk(mvcResult);

        var optionalWebResource = webResourceRepository.findByName(CanObserveResponseBuilder.DEFAULT_WEB_RESOURCE.getName());
        assertTrue(optionalWebResource.isPresent());
        WebResourceEntity webResourceEntity = optionalWebResource.get();
        assertEquals(webResourceId, webResourceEntity.getId());
        assertWebResourceEntity(webResourceEntity);

        var optionalObservingEntity = observingRepository.findByWebResourceIdAndUserId(webResourceId, USER_ID);
        var observingEntity = optionalObservingEntity.get();
        assertWebResourceObservingEntity(observingEntity);

        WebResourceObserving responseDto = getResponseDto(mvcResult, WebResourceObserving.class);
        assertNotNull(responseDto);

        assertResponse(responseDto, webResourceEntity);
    }

    @Test
    void addWebResourceObserving_WhenPluginCanNotObserve_Expect400() {
        //Assign
        prepareDb();
        UUID webResourceId = webResourceRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .status(ObserveStatus.NOT_OBSERVE)
                .build()).getId();
        observingRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .webResource(webResourceRepository.findById(webResourceId).get())
                .user(userRepository.findById(USER_ID).get())
                .status(ObserveStatus.NOT_OBSERVE)
                .build());
        WebResourceObservingAddRequestBody requestBody = prepareAddRequest();

        CanObserveResponse pluginCanObserveResponse = CanObserveResponseBuilder.defaultWebResourceDto()
                .isObservable(false);
        wireMockServer.stubFor(WireMock.post(PLUGIN_CAN_OBSERVE_ENDPOINT)
                .willReturn(wireMockResponse(pluginCanObserveResponse)));

        //Action
        MvcResult mvcResult = post(WEBRESOURCE_OBSERVING_ENDPOINT, requestBody, prepareSimpleUserHeaders());

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.BAD_REQUEST);

        var optionalWebResource = webResourceRepository.findByName(CanObserveResponseBuilder.DEFAULT_WEB_RESOURCE.getName());
        assertTrue(optionalWebResource.isPresent());
        WebResourceEntity webResourceEntity = optionalWebResource.get();
        assertEquals(ObserveStatus.NOT_OBSERVE, webResourceEntity.getStatus());

        var optionalObservingEntity = observingRepository.findByWebResourceIdAndUserId(webResourceId, USER_ID);
        var observingEntity = optionalObservingEntity.get();
        assertEquals(ObserveStatus.NOT_OBSERVE, observingEntity.getStatus());
    }

    @Test
    void addWebResourceObserving_WhenPluginStatusCreated_Expect401() {
        //Assign
        prepareDb();
        PluginEntity pluginEntity = pluginRepository.findById(PLUGIN_ID).get();
        pluginEntity.setStatus(PluginStatus.CREATED);
        pluginRepository.save(pluginEntity);
        WebResourceObservingAddRequestBody requestBody = prepareAddRequest();

        CanObserveResponse pluginCanObserveResponse = CanObserveResponseBuilder.defaultWebResourceDto()
                .isObservable(false);
        wireMockServer.stubFor(WireMock.post(PLUGIN_CAN_OBSERVE_ENDPOINT)
                .willReturn(wireMockResponse(pluginCanObserveResponse)));

        //Action
        MvcResult mvcResult = post(WEBRESOURCE_OBSERVING_ENDPOINT, requestBody, prepareSimpleUserHeaders());

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.UNAUTHORIZED);
    }

    private WebResourceObservingAddRequestBody prepareAddRequest() {
        WebResourceObservingAddRequestBody requestBody = WebResourceObservingAddRequestBodyBuilder.defaultRequest()
                .pluginId(PLUGIN_ID);
        return requestBody;
    }

    private void stubSuccessCanObserve(String expectedName) {
        CanObserveResponse pluginCanObserveResponse = CanObserveResponseBuilder.defaultWebResourceDto()
                .webResource(WebResourceDtoBuilder.defaultWebResourceDto()
                        .name(expectedName));
        wireMockServer.stubFor(WireMock.post(PLUGIN_CAN_OBSERVE_ENDPOINT)
                .willReturn(wireMockResponse(pluginCanObserveResponse)));
    }

    private void stubSuccessWebResourceAdd(String expectedName) {
        WebResourceDto pluginWebResourceAddResponse = WebResourceDtoBuilder.defaultWebResourceDto()
                .name(expectedName);
        wireMockServer.stubFor(WireMock.post(PLUGIN_WEBRESOURCE_ENDPOINT)
                .willReturn(wireMockResponse(pluginWebResourceAddResponse)));
    }

    private void prepareDb() {
        USER_ID = userRepository.save(UserEntityBuilder.defaultBuilder().build()).getId();
        PLUGIN_ID = pluginRepository.save(PluginEntityBuilder.persistedDefaultBuilder()
                .ownerUser(userRepository.findById(USER_ID).get()).build()).getId();
        pluginUsageRepository.save(PluginUsageEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .user(userRepository.findById(USER_ID).get()).build());
    }

    private void assertResponse(WebResourceObserving responseDto, WebResourceEntity webResourceEntity) {
        WebResource webResourceDto = responseDto.getWebResourceDto();
        assertNotNull(webResourceDto);
        assertEquals(webResourceEntity.getId(), webResourceDto.getId());
        assertEquals(CanObserveResponseBuilder.DEFAULT_WEB_RESOURCE.getName(), webResourceDto.getName());
        assertEquals(CanObserveResponseBuilder.DEFAULT_WEB_RESOURCE.getDescription(), webResourceDto.getDescription());

        ObserveSettings observeSettings = responseDto.getObserveSettings();
        assertNotNull(observeSettings);
        assertEquals(true, observeSettings.getNeedNotify());
    }

    private void assertWebResourceEntity(WebResourceEntity webResourceEntity) {
        assertEquals(CanObserveResponseBuilder.DEFAULT_WEB_RESOURCE.getName(), webResourceEntity.getName());
        assertEquals(CanObserveResponseBuilder.DEFAULT_WEB_RESOURCE.getDescription(), webResourceEntity.getDescription());
        assertEquals(ObserveStatus.OBSERVE, webResourceEntity.getStatus());
    }

    private void assertWebResourceObservingEntity(WebResourceObservingEntity observingEntity) {
        assertNotNull(observingEntity.getObserveSettings());
        assertTrue(observingEntity.getObserveSettings().getNeedNotify());
        assertEquals(ObserveStatus.OBSERVE, observingEntity.getStatus());
    }
}
