package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.core.dto.domain.entity.WebResourceObservingDto;
import com.victor.kochnev.core.dto.request.GetWebResourceObservingsRequestDto;
import com.victor.kochnev.core.dto.request.WebResourceObservingsFilterDto;
import com.victor.kochnev.core.dto.response.GetWebResouceObservingsResponseDto;
import com.victor.kochnev.dal.entity.*;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetWebResourceObservingsControllerTest extends BaseControllerTest {
    private static final String GET_WEB_RESOURCE_OBSERVINGS_ENDPOINT = "/webresource/observing";
    private static UUID OWNER_ID;
    private static UUID USER_ID1;
    private static UUID USER_ID2;
    private static UUID USER_ID3;
    private static UUID USER_ID4;
    private static UUID PLUGIN_ID1;
    private static UUID PLUGIN_ID2;
    private static UUID WEBRESOURCE_ID1;
    private static UUID WEBRESOURCE_ID2;
    private static UUID WEBRESOURCE_ID3;
    private static UUID WEBRESOURCE_OBSERVING_ID1;
    private static UUID WEBRESOURCE_OBSERVING_ID2;
    private static UUID WEBRESOURCE_OBSERVING_ID3;
    private static UUID WEBRESOURCE_OBSERVING_ID4;
    private static UserEntity userForRequest;
    @Autowired
    PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Test
    void successGetAll() {
        //Assign
        prepareDb();
        var requestDto = prepareRequest();
        requestDto.getFilters().setPluginIds(List.of(PLUGIN_ID1));
        String uri = getUri(requestDto);

        //Action
        MvcResult mvcResult = get(uri, requestDto, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        var response = getResponseDto(mvcResult, GetWebResouceObservingsResponseDto.class);

        assertNotNull(response);
        assertNotNull(response.getWebResourceObservings());
        assertEquals(2, response.getWebResourceObservings().size());
        assertContains(response.getWebResourceObservings(), WEBRESOURCE_OBSERVING_ID2);
        assertContains(response.getWebResourceObservings(), WEBRESOURCE_OBSERVING_ID3);
    }

    private GetWebResourceObservingsRequestDto prepareRequest() {
        var requestDto = new GetWebResourceObservingsRequestDto();
        requestDto.setFilters(new WebResourceObservingsFilterDto());
        return requestDto;
    }

    private void assertContains(List<WebResourceObservingDto> observings, UUID observingId) {
        assertTrue(observings.stream().anyMatch(observing -> observing.getId().equals(observingId)));
    }

    private String getUri(GetWebResourceObservingsRequestDto requestDto) {
        if (requestDto == null || requestDto.getFilters() == null) {
            return GET_WEB_RESOURCE_OBSERVINGS_ENDPOINT;
        }
        StringBuilder uri = new StringBuilder(GET_WEB_RESOURCE_OBSERVINGS_ENDPOINT);
        boolean isFirst = true;
        if (requestDto.getFilters().getPluginIds() != null) {
            uri.append(isFirst ? "?" : "$");
            requestDto.getFilters().getPluginIds().forEach(
                    observingId -> uri.append("filters.pluginIds=").append(observingId)
            );
            isFirst = false;
        }
        return uri.toString();
    }

    private void prepareDb() {
        OWNER_ID = userRepository.save(UserEntityBuilder.postfixBuilder(1).build()).getId();
        USER_ID1 = userRepository.save(UserEntityBuilder.postfixBuilder(2).build()).getId();
        USER_ID2 = userRepository.save(UserEntityBuilder.postfixBuilder(3).build()).getId();
        USER_ID3 = userRepository.save(UserEntityBuilder.postfixBuilder(4).build()).getId();
        USER_ID4 = userRepository.save(UserEntityBuilder.postfixBuilder(5).build()).getId();

        PLUGIN_ID1 = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .ownerUser(userRepository.findById(OWNER_ID).get())
                .build()).getId();
        PLUGIN_ID2 = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(2)
                .ownerUser(userRepository.findById(OWNER_ID).get())
                .build()).getId();

        WEBRESOURCE_ID1 = webResourceRepository.save(WebResourceEntityBuilder.persistedPostfixBuilder(1)
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        WEBRESOURCE_ID2 = webResourceRepository.save(WebResourceEntityBuilder.persistedPostfixBuilder(2)
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        WEBRESOURCE_ID3 = webResourceRepository.save(WebResourceEntityBuilder.persistedPostfixBuilder(3)
                .plugin(pluginRepository.findById(PLUGIN_ID2).get())
                .build()).getId();

        WEBRESOURCE_OBSERVING_ID1 = observingRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(1)
                .webResource(webResourceRepository.findById(WEBRESOURCE_ID1).get())
                .user(userRepository.findById(USER_ID1).get())
                .build()).getId();
        WEBRESOURCE_OBSERVING_ID2 = observingRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(1)
                .webResource(webResourceRepository.findById(WEBRESOURCE_ID1).get())
                .user(userRepository.findById(USER_ID2).get())
                .build()).getId();
        WEBRESOURCE_OBSERVING_ID3 = observingRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(1)
                .webResource(webResourceRepository.findById(WEBRESOURCE_ID2).get())
                .user(userRepository.findById(USER_ID2).get())
                .build()).getId();
        WEBRESOURCE_OBSERVING_ID4 = observingRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(1)
                .webResource(webResourceRepository.findById(WEBRESOURCE_ID3).get())
                .user(userRepository.findById(USER_ID2).get())
                .build()).getId();

        userForRequest = userRepository.findById(USER_ID2).get();
    }
}
