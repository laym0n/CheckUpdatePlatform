package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.WebResourceObservingRepository;
import com.victor.kochnev.dal.BaseBootTest;
import com.victor.kochnev.dal.entity.*;
import com.victor.kochnev.domain.entity.WebResourceObserving;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
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
//        UUID userId1 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(1).build()).getId();
//        UUID userId2 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(2).build()).getId();
//        UUID userId3 = userEntityRepository.save(UserEntityBuilder.postfixBuilder(3).build()).getId();
//        UUID ownerUserId = userEntityRepository.save(UserEntityBuilder.postfixBuilder(9).build()).getId();
//
//        UUID pluginId = pluginEntityRepository.save(PluginEntityBuilder.persistedDefaultBuilder()
//                .ownerUser(userEntityRepository.findById(ownerUserId).get()).build()).getId();
//        UUID webResourceId = webResourceEntityRepository.save(WebResourceEntityBuilder.persistedDefaultBuilder()
//                .plugin(pluginEntityRepository.findById(pluginId).get()).build()).getId();
//
//        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
//                .user(userEntityRepository.findById(userId1).get())
//                .plugin(pluginEntityRepository.findById(pluginId).get())
//                .expiredDate(null).build());
//        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
//                .user(userEntityRepository.findById(userId2).get())
//                .plugin(pluginEntityRepository.findById(pluginId).get())
//                .expiredDate(ZonedDateTime.now().minusMinutes(5)).build());
//        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
//                .user(userEntityRepository.findById(userId3).get())
//                .plugin(pluginEntityRepository.findById(pluginId).get())
//                .expiredDate(ZonedDateTime.now().plusMinutes(5)).build());
//
//        webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
//                .user(userEntityRepository.findById(userId1).get())
//                .webResource(webResourceEntityRepository.findById(webResourceId).get()).build());
//        webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
//                .user(userEntityRepository.findById(userId2).get())
//                .webResource(webResourceEntityRepository.findById(webResourceId).get()).build());
//        webResourceObservingEntityRepository.save(WebResourceObservingEntityBuilder.persistedDefaultBuilder()
//                .user(userEntityRepository.findById(userId3).get())
//                .webResource(webResourceEntityRepository.findById(webResourceId).get()).build());
//
//        //Action
//        Optional<WebResourceObserving> optionalWebResourceObserving = webResourceObservingRepository.findAllWithExpiredDateAfterOrNull();
//
//        //Assert
//        assertNotNull(optionalWebResourceObserving);
//        assertTrue(optionalWebResourceObserving.isPresent());
//
//        WebResourceObserving webResourceObserving = optionalWebResourceObserving.get();
//        assertEquals(true, webResourceObserving.getObserveSettings().needNotify());
//        assertEquals(WebResourceObservingEntityBuilder.DEFAULT_STATUS, webResourceObserving.getStatus());
//        assertNotNull(webResourceObserving.getWebResource());
//        assertNotNull(webResourceObserving.getUser());
    }
}
