package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.dto.dal.GetWebResourceObservingDalRequestDto;
import com.victor.kochnev.core.dto.dal.WebResourceObservingFilterDalDto;
import com.victor.kochnev.core.repository.WebResourceObservingRepository;
import com.victor.kochnev.dal.BaseBootTest;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.dal.entity.WebResourceEntityBuilder;
import com.victor.kochnev.dal.entity.WebResourceObservingEntityBuilder;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetByFiltersWebResourceObservingRepositoryTest extends BaseBootTest {
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
    @Autowired
    WebResourceObservingRepository webResourceObservingRepository;

    @Test
    void testGetByFilters_byUserId() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setUserIds(List.of(USER_ID2));

        //Action
        var response = webResourceObservingRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertEquals(3, response.getWebResourceObservings().size());

        assertContains(response.getWebResourceObservings(), WEBRESOURCE_OBSERVING_ID2);
        assertContains(response.getWebResourceObservings(), WEBRESOURCE_OBSERVING_ID3);
        assertContains(response.getWebResourceObservings(), WEBRESOURCE_OBSERVING_ID4);
    }

    @Test
    void testGetByFilters_byPluginId() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setPluginIds(List.of(PLUGIN_ID2));

        //Action
        var response = webResourceObservingRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertEquals(1, response.getWebResourceObservings().size());

        assertContains(response.getWebResourceObservings(), WEBRESOURCE_OBSERVING_ID4);
    }

    @Test
    void testGetByFilters_byPluginIdAndUserId() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setPluginIds(List.of(PLUGIN_ID1));
        request.getFilters().setUserIds(List.of(USER_ID2));

        //Action
        var response = webResourceObservingRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertEquals(2, response.getWebResourceObservings().size());

        assertContains(response.getWebResourceObservings(), WEBRESOURCE_OBSERVING_ID2);
        assertContains(response.getWebResourceObservings(), WEBRESOURCE_OBSERVING_ID3);
    }

    private void assertContains(List<WebResourceObserving> webResourceObservings, UUID observingId) {
        assertTrue(webResourceObservings.stream().anyMatch(observing -> observing.getId().equals(observingId)));
    }

    private GetWebResourceObservingDalRequestDto prepareRequest() {
        var request = new GetWebResourceObservingDalRequestDto();
        request.setFilters(new WebResourceObservingFilterDalDto());
        return request;
    }

    private void prepareDb() {
        OWNER_ID = userEntityRepository.save(UserEntityBuilder.postfixBuilder(1).build()).getId();
        USER_ID1 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(2).build()).getId();
        USER_ID2 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(3).build()).getId();
        USER_ID3 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(4).build()).getId();
        USER_ID4 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(5).build()).getId();

        PLUGIN_ID1 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .ownerUser(userEntityRepository.findById(OWNER_ID).get())
                .build()).getId();
        PLUGIN_ID2 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(2)
                .ownerUser(userEntityRepository.findById(OWNER_ID).get())
                .build()).getId();

        WEBRESOURCE_ID1 = webResourceEntityRepository.save(WebResourceEntityBuilder.persistedPostfixBuilder(1)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        WEBRESOURCE_ID2 = webResourceEntityRepository.save(WebResourceEntityBuilder.persistedPostfixBuilder(2)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        WEBRESOURCE_ID3 = webResourceEntityRepository.save(WebResourceEntityBuilder.persistedPostfixBuilder(3)
                .plugin(pluginEntityRepository.findById(PLUGIN_ID2).get())
                .build()).getId();

        WEBRESOURCE_OBSERVING_ID1 = webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(1)
                .webResource(webResourceEntityRepository.findById(WEBRESOURCE_ID1).get())
                .user(userEntityRepository.findById(USER_ID1).get())
                .build()).getId();
        WEBRESOURCE_OBSERVING_ID2 = webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(1)
                .webResource(webResourceEntityRepository.findById(WEBRESOURCE_ID1).get())
                .user(userEntityRepository.findById(USER_ID2).get())
                .build()).getId();
        WEBRESOURCE_OBSERVING_ID3 = webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(1)
                .webResource(webResourceEntityRepository.findById(WEBRESOURCE_ID2).get())
                .user(userEntityRepository.findById(USER_ID2).get())
                .build()).getId();
        WEBRESOURCE_OBSERVING_ID4 = webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedPostfixBuilder(1)
                .webResource(webResourceEntityRepository.findById(WEBRESOURCE_ID3).get())
                .user(userEntityRepository.findById(USER_ID2).get())
                .build()).getId();
    }
}
