package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.WebResourceObservingRepository;
import com.victor.kochnev.dal.BaseBootTest;
import com.victor.kochnev.dal.entity.*;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import com.victor.kochnev.domain.enums.ObserveStatus;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class WebResourceObservingRepositoryTest extends BaseBootTest {
    @Autowired
    private WebResourceObservingRepository webResourceObservingRepository;

    @Test
    void testFindByWebResourceIdAndUserId() {
        //Assign
        UUID userId = userEntityRepository.save(UserEntityBuilder.persistedDefaultBuilder().build()).getId();
        UUID pluginId = pluginEntityRepository.save(PluginEntityBuilder.persistedDefaultBuilder()
                .ownerUser(userEntityRepository.findById(userId).get()).build()).getId();
        UUID webResourceId = webResourceEntityRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginEntityRepository.findById(pluginId).get()).build()).getId();
        webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(userId).get())
                .webResource(webResourceEntityRepository.findById(webResourceId).get()).build());

        //Action
        Optional<WebResourceObserving> optionalWebResourceObserving = webResourceObservingRepository.findByWebResourceIdAndUserId(webResourceId, userId);

        //Assert
        assertNotNull(optionalWebResourceObserving);
        assertTrue(optionalWebResourceObserving.isPresent());

        WebResourceObserving webResourceObserving = optionalWebResourceObserving.get();
        assertEquals(true, webResourceObserving.getObserveSettings().needNotify());
        assertEquals(WebResourceObservingEntityBuilder.DEFAULT_STATUS, webResourceObserving.getStatus());
        assertNotNull(webResourceObserving.getWebResource());
        assertNotNull(webResourceObserving.getUser());
    }

    @Test
    void testFindByWebResourceIdAndUserId_WhenNotExists_ExpectEmptyOptional() {
        //Action
        Optional<WebResourceObserving> optionalWebResourceObserving = webResourceObservingRepository.findByWebResourceIdAndUserId(UUID.randomUUID(), UUID.randomUUID());

        //Assert
        assertNotNull(optionalWebResourceObserving);
        assertTrue(optionalWebResourceObserving.isEmpty());
    }

    @Test
    void testFindByWebResourceIdAndUserId_MultipleResult() {
        //Assign
        UUID userId1 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(1).build()).getId();
        UUID userId2 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(2).build()).getId();
        UUID userId3 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(3).build()).getId();
        UUID ownerUserId = userEntityRepository.save(UserEntityBuilder.postfixBuilder(9).build()).getId();

        UUID pluginId1 = pluginEntityRepository.save(PluginEntityBuilder.persistedDefaultBuilder()
                .ownerUser(userEntityRepository.findById(ownerUserId).get()).build()).getId();
        UUID webResourceId1 = webResourceEntityRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginEntityRepository.findById(pluginId1).get()).build()).getId();

        UUID pluginId2 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .ownerUser(userEntityRepository.findById(ownerUserId).get()).build()).getId();
        UUID webResourceId2 = webResourceEntityRepository.save(WebResourceEntityBuilder.persistedPostfixBuilder(1)
                .plugin(pluginEntityRepository.findById(pluginId2).get()).build()).getId();

        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
                .user(userEntityRepository.findById(userId1).get())
                .plugin(pluginEntityRepository.findById(pluginId1).get())
                .expiredDate(null).build());
        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(2)
                .user(userEntityRepository.findById(userId2).get())
                .plugin(pluginEntityRepository.findById(pluginId1).get())
                .expiredDate(ZonedDateTime.now().minusMinutes(5)).build());
        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(3)
                .user(userEntityRepository.findById(userId3).get())
                .plugin(pluginEntityRepository.findById(pluginId1).get())
                .expiredDate(ZonedDateTime.now().plusMinutes(5)).build()).getId();

        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(4)
                .user(userEntityRepository.findById(userId1).get())
                .plugin(pluginEntityRepository.findById(pluginId2).get())
                .expiredDate(null).build());
        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(5)
                .user(userEntityRepository.findById(userId2).get())
                .plugin(pluginEntityRepository.findById(pluginId2).get())
                .expiredDate(ZonedDateTime.now().minusMinutes(5)).build());
        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(6)
                .user(userEntityRepository.findById(userId3).get())
                .plugin(pluginEntityRepository.findById(pluginId2).get())
                .expiredDate(ZonedDateTime.now().plusMinutes(5)).build()).getId();

        UUID observingId1 = webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(userId1).get())
                .webResource(webResourceEntityRepository.findById(webResourceId1).get()).build()).getId();
        webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(userId2).get())
                .webResource(webResourceEntityRepository.findById(webResourceId1).get()).build());
        UUID observingId3 = webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(userId3).get())
                .webResource(webResourceEntityRepository.findById(webResourceId1).get()).build()).getId();

        webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(userId1).get())
                .webResource(webResourceEntityRepository.findById(webResourceId2).get()).build()).getId();
        webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(userId2).get())
                .webResource(webResourceEntityRepository.findById(webResourceId2).get()).build());
        webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(userId3).get())
                .webResource(webResourceEntityRepository.findById(webResourceId2).get()).build()).getId();

        //Action
        List<WebResourceObserving> observingList = webResourceObservingRepository.findAllWithExpiredDateAfterOrNull(WebResourceEntityBuilder.DEFAULT_NAME, ZonedDateTime.now());

        //Assert
        assertNotNull(observingList);
        assertEquals(2, observingList.size());

        assertTrue(observingList.stream().anyMatch(i -> i.getId().equals(observingId1)));
        assertTrue(observingList.stream().anyMatch(i -> i.getId().equals(observingId3)));
    }

    @ParameterizedTest
    @CsvSource(value = {"OBSERVE,NOT_OBSERVE", "NOT_OBSERVE,OBSERVE"})
    void testCountObserversWithStatus(ObserveStatus targetStatus, ObserveStatus otherStatus) {
        //Assign
        UUID userId1 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(1).build()).getId();
        UUID userId2 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(2).build()).getId();
        UUID userId3 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(3).build()).getId();
        UUID ownerUserId = userEntityRepository.save(UserEntityBuilder.postfixBuilder(9).build()).getId();

        UUID pluginId = pluginEntityRepository.save(PluginEntityBuilder.persistedDefaultBuilder()
                .ownerUser(userEntityRepository.findById(ownerUserId).get()).build()).getId();
        UUID webResourceId = webResourceEntityRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
                .plugin(pluginEntityRepository.findById(pluginId).get()).build()).getId();

        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
                .user(userEntityRepository.findById(userId1).get())
                .plugin(pluginEntityRepository.findById(pluginId).get())
                .expiredDate(null).build());
        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(2)
                .user(userEntityRepository.findById(userId2).get())
                .plugin(pluginEntityRepository.findById(pluginId).get())
                .expiredDate(ZonedDateTime.now().minusMinutes(5)).build());
        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(3)
                .user(userEntityRepository.findById(userId3).get())
                .plugin(pluginEntityRepository.findById(pluginId).get())
                .expiredDate(ZonedDateTime.now().plusMinutes(5)).build()).getId();

        UUID observingId1 = webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(userId1).get())
                .webResource(webResourceEntityRepository.findById(webResourceId).get())
                .status(targetStatus).build()).getId();
        webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(userId2).get())
                .webResource(webResourceEntityRepository.findById(webResourceId).get())
                .status(targetStatus).build());
        UUID observingId3 = webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(userId3).get())
                .webResource(webResourceEntityRepository.findById(webResourceId).get())
                .status(otherStatus).build()).getId();

        //Action
        int count = webResourceObservingRepository.countObserversWithStatus(webResourceId, targetStatus);

        //Assert
        assertEquals(2, count);
    }
}
