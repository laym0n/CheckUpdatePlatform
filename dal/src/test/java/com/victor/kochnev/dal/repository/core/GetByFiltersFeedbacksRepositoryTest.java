package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.dto.dal.FeedbacksFilterDalDto;
import com.victor.kochnev.core.dto.dal.GetFeedbacksDalRequestDto;
import com.victor.kochnev.core.repository.FeedbackRepository;
import com.victor.kochnev.dal.BaseBootTest;
import com.victor.kochnev.dal.entity.FeedbackEntityBuilder;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.domain.entity.Feedback;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetByFiltersFeedbacksRepositoryTest extends BaseBootTest {
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
    FeedbackRepository feedbackRepository;

    @Test
    void testGetByFilters_All() {
        //Assign
        prepareDb();
        var request = prepareRequest();

        //Action
        var response = feedbackRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertEquals(6, response.getFeedbacks().size());
    }

    @Test
    void testGetByFiltersByIds() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setIds(List.of(FEEDBACK_ID2, FEEDBACK_ID6));

        //Action
        var response = feedbackRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertEquals(2, response.getFeedbacks().size());
        assertContains(response.getFeedbacks(), FEEDBACK_ID2);
        assertContains(response.getFeedbacks(), FEEDBACK_ID6);
    }

    @Test
    void testGetByFiltersByPluginIds() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setPluginIds(List.of(PLUGIN_ID2));

        //Action
        var response = feedbackRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertEquals(1, response.getFeedbacks().size());
        assertContains(response.getFeedbacks(), FEEDBACK_ID6);
    }

    @Test
    void testGetByFiltersByUserIds() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setUserIds(List.of(USER_ID5));

        //Action
        var response = feedbackRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertEquals(2, response.getFeedbacks().size());
        assertContains(response.getFeedbacks(), FEEDBACK_ID5);
        assertContains(response.getFeedbacks(), FEEDBACK_ID6);
    }

    @Test
    void testGetByFiltersByExcludedUserIds() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setExcludedUserIds(List.of(USER_ID5));

        //Action
        var response = feedbackRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertEquals(4, response.getFeedbacks().size());
        assertContains(response.getFeedbacks(), FEEDBACK_ID1);
        assertContains(response.getFeedbacks(), FEEDBACK_ID2);
        assertContains(response.getFeedbacks(), FEEDBACK_ID3);
        assertContains(response.getFeedbacks(), FEEDBACK_ID4);
    }

    private void assertContains(List<Feedback> entities, UUID id) {
        assertTrue(entities.stream().anyMatch(entity -> entity.getId().equals(id)));
    }

    private GetFeedbacksDalRequestDto prepareRequest() {
        var request = new GetFeedbacksDalRequestDto();
        request.setFilters(new FeedbacksFilterDalDto());
        return request;
    }

    private void prepareDb() {
        OWNER_ID = userEntityRepository.save(UserEntityBuilder.postfixBuilder(0).build()).getId();
        USER_ID1 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(1).build()).getId();
        USER_ID2 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(2).build()).getId();
        USER_ID3 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(3).build()).getId();
        USER_ID4 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(4).build()).getId();
        USER_ID5 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(5).build()).getId();

        PLUGIN_ID1 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .ownerUser(userEntityRepository.findById(OWNER_ID).get())
                .build()).getId();
        PLUGIN_ID2 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(2)
                .ownerUser(userEntityRepository.findById(OWNER_ID).get())
                .build()).getId();

        FEEDBACK_ID1 = feedbackEntityRepository.save(FeedbackEntityBuilder.persistedPostfixBuilder(1)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .user(userEntityRepository.findById(USER_ID1).get())
                .build()).getId();
        FEEDBACK_ID2 = feedbackEntityRepository.save(FeedbackEntityBuilder.persistedPostfixBuilder(2)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .user(userEntityRepository.findById(USER_ID2).get())
                .build()).getId();
        FEEDBACK_ID3 = feedbackEntityRepository.save(FeedbackEntityBuilder.persistedPostfixBuilder(3)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .user(userEntityRepository.findById(USER_ID3).get())
                .build()).getId();
        FEEDBACK_ID4 = feedbackEntityRepository.save(FeedbackEntityBuilder.persistedPostfixBuilder(4)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .user(userEntityRepository.findById(USER_ID4).get())
                .build()).getId();
        FEEDBACK_ID5 = feedbackEntityRepository.save(FeedbackEntityBuilder.persistedPostfixBuilder(5)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .user(userEntityRepository.findById(USER_ID5).get())
                .build()).getId();
        FEEDBACK_ID6 = feedbackEntityRepository.save(FeedbackEntityBuilder.persistedPostfixBuilder(6)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID2).get())
                .user(userEntityRepository.findById(USER_ID5).get())
                .build()).getId();
    }
}
