package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.core.dto.request.FeedbacksFilterDto;
import com.victor.kochnev.core.dto.request.GetFeedbacksRequestDto;
import com.victor.kochnev.core.dto.response.GetFeedbacksResponseDto;
import com.victor.kochnev.dal.entity.FeedbackEntityBuilder;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import lombok.SneakyThrows;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.test.web.servlet.MvcResult;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class GetFeedbacksControllerTest extends BaseControllerTest {
    private static final String FEEDBACK_ENDPOINT = "/feedback";
    private static UUID OWNER_ID;
    private static UUID USER_ID1;
    private static UUID USER_ID2;
    private static UUID USER_ID3;
    private static UUID USER_ID4;
    private static UUID USER_ID5;
    private static UUID PLUGIN_ID1;
    private static UUID PLUGIN_ID2;
    private static UUID FEEDBACK_ID1;
    private static UUID FEEDBACK_ID2;
    private static UUID FEEDBACK_ID3;
    private static UUID FEEDBACK_ID4;
    private static UUID FEEDBACK_ID5;
    private static UUID FEEDBACK_ID6;
    @Autowired
    PasswordEncoder passwordEncoder;

    @SneakyThrows
    @Test
    void successGetAll() {
        //Assign
        prepareDb();
        var requestDto = prepareRequest();

        //Action
        MvcResult mvcResult = get(FEEDBACK_ENDPOINT, requestDto, null);

        //Assert
        assertHttpStatusOk(mvcResult);

        var response = getResponseDto(mvcResult, GetFeedbacksResponseDto.class);

        assertNotNull(response);
        assertNotNull(response.getFeedbacks());
        assertEquals(6, response.getFeedbacks().size());
    }

    private GetFeedbacksRequestDto prepareRequest() {
        var request = new GetFeedbacksRequestDto();
        request.setFilters(new FeedbacksFilterDto());
        return request;
    }

    void prepareDb() {
        OWNER_ID = userRepository.save(UserEntityBuilder.postfixBuilder(0).build()).getId();
        USER_ID1 = userRepository.save(UserEntityBuilder.postfixBuilder(1).build()).getId();
        USER_ID2 = userRepository.save(UserEntityBuilder.postfixBuilder(2).build()).getId();
        USER_ID3 = userRepository.save(UserEntityBuilder.postfixBuilder(3).build()).getId();
        USER_ID4 = userRepository.save(UserEntityBuilder.postfixBuilder(4).build()).getId();
        USER_ID5 = userRepository.save(UserEntityBuilder.postfixBuilder(5).build()).getId();

        PLUGIN_ID1 = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .ownerUser(userRepository.findById(OWNER_ID).get())
                .build()).getId();
        PLUGIN_ID2 = pluginRepository.save(PluginEntityBuilder.persistedPostfixBuilder(2)
                .ownerUser(userRepository.findById(OWNER_ID).get())
                .build()).getId();

        FEEDBACK_ID1 = feedbackRepository.save(FeedbackEntityBuilder.persistedPostfixBuilder(1)
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .user(userRepository.findById(USER_ID1).get())
                .build()).getId();
        FEEDBACK_ID2 = feedbackRepository.save(FeedbackEntityBuilder.persistedPostfixBuilder(2)
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .user(userRepository.findById(USER_ID2).get())
                .build()).getId();
        FEEDBACK_ID3 = feedbackRepository.save(FeedbackEntityBuilder.persistedPostfixBuilder(3)
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .user(userRepository.findById(USER_ID3).get())
                .build()).getId();
        FEEDBACK_ID4 = feedbackRepository.save(FeedbackEntityBuilder.persistedPostfixBuilder(4)
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .user(userRepository.findById(USER_ID4).get())
                .build()).getId();
        FEEDBACK_ID5 = feedbackRepository.save(FeedbackEntityBuilder.persistedPostfixBuilder(5)
                .plugin(pluginRepository.findById(PLUGIN_ID1).get())
                .user(userRepository.findById(USER_ID5).get())
                .build()).getId();
        FEEDBACK_ID6 = feedbackRepository.save(FeedbackEntityBuilder.persistedPostfixBuilder(6)
                .plugin(pluginRepository.findById(PLUGIN_ID2).get())
                .user(userRepository.findById(USER_ID5).get())
                .build()).getId();
    }
}
