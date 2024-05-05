package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.core.dto.domain.entity.FeedbackDto;
import com.victor.kochnev.core.dto.request.CreateOrUpdateFeedbackRequestDto;
import com.victor.kochnev.dal.entity.*;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;

class CreateFeedbackControllerTest extends BaseControllerTest {
    private final String FEEDBACK_ENDPOINT = "/feedback";
    private UUID USER_ID;
    private UUID PLUGIN_ID;
    private UserEntity userForRequest;

    @Test
    void successCreateFeedback() {
        //Assign
        prepareDb();
        var requestBody = prepareRequest();

        //Action
        MvcResult mvcResult = post(FEEDBACK_ENDPOINT, requestBody, prepareUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        var feedbackDto = getResponseDto(mvcResult, FeedbackDto.class);

        assertEquals(requestBody.getComment(), feedbackDto.getComment());
        assertEquals(requestBody.getRating(), feedbackDto.getRating());

        List<FeedbackEntity> feedbacks = feedbackRepository.findAll();
        assertEquals(1, feedbacks.size());
        FeedbackEntity feedbackEntity = feedbacks.get(0);
        assertEquals(requestBody.getComment(), feedbackEntity.getComment());
        assertEquals(requestBody.getRating(), feedbackEntity.getRating());
    }

    @Test
    void successUpdateFeedback() {
        //Assign
        prepareDb();
        feedbackRepository.save(FeedbackEntityBuilder.persistedBuilder(1)
                .user(userRepository.findById(userForRequest.getId()).get())
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .rating(1)
                .comment("asdasd")
                .build());
        var requestBody = prepareRequest();

        //Action
        MvcResult mvcResult = post(FEEDBACK_ENDPOINT, requestBody, prepareUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        var feedbackDto = getResponseDto(mvcResult, FeedbackDto.class);

        assertEquals(requestBody.getComment(), feedbackDto.getComment());
        assertEquals(requestBody.getRating(), feedbackDto.getRating());

        List<FeedbackEntity> feedbacks = feedbackRepository.findAll();
        assertEquals(1, feedbacks.size());
        FeedbackEntity feedbackEntity = feedbacks.get(0);
        assertEquals(requestBody.getComment(), feedbackEntity.getComment());
        assertEquals(requestBody.getRating(), feedbackEntity.getRating());
    }

    @Test
    void createFeedback_WhenNotAllowed_Expect401() {
        prepareDb();
        userForRequest = userRepository.save(UserEntityBuilder.postfixBuilder(2).build());
        var requestBody = prepareRequest();

        //Action
        MvcResult mvcResult = post(FEEDBACK_ENDPOINT, requestBody, prepareUserHeaders(userForRequest));

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.UNAUTHORIZED);

        List<FeedbackEntity> feedbacks = feedbackRepository.findAll();
        assertEquals(0, feedbacks.size());
    }

    private CreateOrUpdateFeedbackRequestDto prepareRequest() {
        var request = new CreateOrUpdateFeedbackRequestDto();
        request.setComment("Comment");
        request.setRating(5);
        request.setPluginId(PLUGIN_ID);
        return request;
    }

    void prepareDb() {
        USER_ID = userRepository.save(UserEntityBuilder.postfixBuilder(1).build()).getId();

        PLUGIN_ID = pluginRepository.save(PluginEntityBuilder.persistedDefaultBuilder()
                .ownerUser(userRepository.findById(USER_ID).get()).build()).getId();

        pluginUsageRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
                .user(userRepository.findById(USER_ID).get())
                .plugin(pluginRepository.findById(PLUGIN_ID).get())
                .build());

        userForRequest = userRepository.findById(USER_ID).get();
    }
}
