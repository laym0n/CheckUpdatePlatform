package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.core.dto.domain.entity.PluginUsageDto;
import com.victor.kochnev.core.dto.request.GetPluginUsagesRequestDto;
import com.victor.kochnev.core.dto.request.PluginUsagesFilterDto;
import com.victor.kochnev.core.dto.response.GetPluginUsagesResponseDto;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.PluginUsageEntityBuilder;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetPluginUsagesControllerTest extends BaseControllerTest {
    private static final String GET_PLUGIN_USAGES_ENDPOINT = "/plugin/usage";
    private static UUID OWNER_ID;
    private static UUID USER_ID1;
    private static UUID USER_ID2;
    private static UUID PLUGIN_ID1;
    private static UUID PLUGIN_ID2;
    private static UUID PLUGIN_USAGE_ID1;
    private static UUID PLUGIN_USAGE_ID2;
    private static UUID PLUGIN_USAGE_ID3;
    private static UUID PLUGIN_USAGE_ID4;
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
        MvcResult mvcResult = get(uri, requestDto, prepareUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        var response = getResponseDto(mvcResult, GetPluginUsagesResponseDto.class);

        assertNotNull(response);
        assertNotNull(response.getPluginUsages());
        assertEquals(1, response.getPluginUsages().size());
        assertContains(response.getPluginUsages(), PLUGIN_USAGE_ID1);
    }

    private GetPluginUsagesRequestDto prepareRequest() {
        var requestDto = new GetPluginUsagesRequestDto();
        requestDto.setFilters(new PluginUsagesFilterDto());
        return requestDto;
    }

    private void assertContains(List<PluginUsageDto> usages, UUID id) {
        assertTrue(usages.stream().anyMatch(usage -> usage.getId().equals(id)));
    }

    private String getUri(GetPluginUsagesRequestDto requestDto) {
        if (requestDto == null || requestDto.getFilters() == null) {
            return GET_PLUGIN_USAGES_ENDPOINT;
        }
        StringBuilder uri = new StringBuilder(GET_PLUGIN_USAGES_ENDPOINT);
        boolean isFirst = true;
        if (requestDto.getFilters().getPluginIds() != null) {
            uri.append(isFirst ? "?" : "$");
            requestDto.getFilters().getPluginIds().forEach(
                    id -> uri.append("filters.pluginIds=").append(id)
            );
            isFirst = false;
        }
        return uri.toString();
    }

    private void prepareDb() {
        OWNER_ID = userRepository.save(UserEntityBuilder.persistedPostfixBuilder(1).build()).getId();
        USER_ID1 = userRepository.save(UserEntityBuilder.persistedPostfixBuilder(2).build()).getId();
        USER_ID2 = userRepository.save(UserEntityBuilder.persistedPostfixBuilder(3).build()).getId();

        PLUGIN_ID1 = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .ownerUser(userRepository.findById(OWNER_ID).get())
                .build()).getId();
        PLUGIN_ID2 = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(2)
                .ownerUser(userRepository.findById(OWNER_ID).get())
                .build()).getId();

        PLUGIN_USAGE_ID1 = pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
                .user(userRepository.findById(USER_ID1).get())
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        PLUGIN_USAGE_ID2 = pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(2)
                .user(userRepository.findById(USER_ID1).get())
                .plugin(pluginRepository.findById(PLUGIN_ID2).get())
                .build()).getId();
        PLUGIN_USAGE_ID3 = pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(3)
                .user(userRepository.findById(USER_ID2).get())
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        PLUGIN_USAGE_ID4 = pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(4)
                .user(userRepository.findById(USER_ID2).get())
                .plugin(pluginRepository.findById(PLUGIN_ID2).get())
                .build()).getId();

        userForRequest = userRepository.findById(USER_ID1).get();
    }
}
