package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.api.dto.CreatePluginUsageRequest;
import com.victor.kochnev.api.dto.DistributionMethod;
import com.victor.kochnev.api.dto.DistributionPlanTypeEnum;
import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescriptionBuilder;
import com.victor.kochnev.dal.entity.*;
import com.victor.kochnev.domain.enums.DistributionPlanType;
import com.victor.kochnev.domain.enums.PluginStatus;
import com.victor.kochnev.domain.enums.UserRole;
import com.victor.kochnev.domain.value.object.DistributionMethodBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.http.HttpStatus;
import org.springframework.test.web.servlet.MvcResult;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PluginUsageCreateTest extends BaseControllerTest {
    private static final String PLUGIN_USAGE_CREATE_ENDPOINT = "/plugin/usage";
    private UUID USER_ID;
    private UUID PLUGIN_ID;
    private UserEntity userForRequest;

    @Test
    void createPluginUsage_WhenPurchase() {
        //Assign
        prepareDb();

        var requestBody = prepareRequest();

        //Action
        MvcResult mvcResult = post(PLUGIN_USAGE_CREATE_ENDPOINT, requestBody, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        Optional<PluginUsageEntity> optionalPluginUsage = pluginUsageRepository.findByPluginIdAndUserId(PLUGIN_ID, USER_ID);
        assertTrue(optionalPluginUsage.isPresent());
        PluginUsageEntity pluginUsage = optionalPluginUsage.get();
        assertNull(pluginUsage.getExpiredDate());
        assertNotNull(pluginUsage.getDistributionMethod());
        assertEquals(DistributionPlanType.PURCHASE, pluginUsage.getDistributionMethod().getType());
        assertEquals(0, DistributionMethodBuilder.DEFAULT_COST.compareTo(pluginUsage.getDistributionMethod().getCost()));
        assertNull(pluginUsage.getDistributionMethod().getDuration());
    }

    @Test
    void createPluginUsage_WhenSubscribe() {
        //Assign
        prepareDb();
        PluginEntity pluginEntity = pluginRepository.findById(PLUGIN_ID).get();
        pluginEntity.getDescription().setDistributionMethods(List.of(DistributionMethodBuilder.defaultSubscribeDistribution()));
        pluginRepository.save(pluginEntity);

        var requestBody = prepareRequest();
        requestBody.setDistributionMethod(new DistributionMethod()
                .type(DistributionPlanTypeEnum.SUBSCRIBE)
                .cost(DistributionMethodBuilder.DEFAULT_COST)
                .duration(DistributionMethodBuilder.DEFAULT_DURATION.toString()));

        //Action
        MvcResult mvcResult = post(PLUGIN_USAGE_CREATE_ENDPOINT, requestBody, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        Optional<PluginUsageEntity> optionalPluginUsage = pluginUsageRepository.findByPluginIdAndUserId(PLUGIN_ID, USER_ID);
        assertTrue(optionalPluginUsage.isPresent());
        PluginUsageEntity pluginUsage = optionalPluginUsage.get();
        assertTrue(ZonedDateTime.now().plus(DistributionMethodBuilder.DEFAULT_DURATION).truncatedTo(ChronoUnit.MINUTES).isEqual(
                pluginUsage.getExpiredDate().truncatedTo(ChronoUnit.MINUTES)));
        assertNotNull(pluginUsage.getDistributionMethod());
        assertEquals(DistributionPlanType.SUBSCRIBE, pluginUsage.getDistributionMethod().getType());
        assertEquals(0, DistributionMethodBuilder.DEFAULT_COST.compareTo(pluginUsage.getDistributionMethod().getCost()));
        assertEquals(DistributionMethodBuilder.DEFAULT_DURATION, pluginUsage.getDistributionMethod().getDuration());
    }

    @Test
    void createPluginUsage_WhenNotFindDistributionMethod() {
        //Assign
        prepareDb();
        PluginEntity pluginEntity = pluginRepository.findById(PLUGIN_ID).get();
        pluginEntity.getDescription().setDistributionMethods(List.of(DistributionMethodBuilder.defaultSubscribeDistribution()));
        pluginRepository.save(pluginEntity);

        var requestBody = prepareRequest();
        requestBody.setDistributionMethod(new DistributionMethod()
                .type(DistributionPlanTypeEnum.SUBSCRIBE)
                .cost(DistributionMethodBuilder.DEFAULT_COST)
                .duration(DistributionMethodBuilder.DEFAULT_DURATION.plusMinutes(1).toString()));

        //Action
        MvcResult mvcResult = post(PLUGIN_USAGE_CREATE_ENDPOINT, requestBody, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.NOT_FOUND);

        Optional<PluginUsageEntity> optionalPluginUsage = pluginUsageRepository.findByPluginIdAndUserId(PLUGIN_ID, USER_ID);
        assertFalse(optionalPluginUsage.isPresent());
    }

    @Test
    void createPluginUsage_WhenPluginStatusIsCreated() {
        //Assign
        prepareDb();
        PluginEntity pluginEntity = pluginRepository.findById(PLUGIN_ID).get();
        pluginEntity.setStatus(PluginStatus.CREATED);
        pluginRepository.save(pluginEntity);

        var requestBody = prepareRequest();

        //Action
        MvcResult mvcResult = post(PLUGIN_USAGE_CREATE_ENDPOINT, requestBody, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatus(mvcResult, HttpStatus.UNAUTHORIZED);

        Optional<PluginUsageEntity> optionalPluginUsage = pluginUsageRepository.findByPluginIdAndUserId(PLUGIN_ID, USER_ID);
        assertFalse(optionalPluginUsage.isPresent());
    }

    private CreatePluginUsageRequest prepareRequest() {
        var requestBody = new CreatePluginUsageRequest();
        requestBody.setPluginId(PLUGIN_ID);
        requestBody.setDistributionMethod(new DistributionMethod()
                .cost(DistributionMethodBuilder.DEFAULT_COST)
                .type(DistributionPlanTypeEnum.PURCHASE));
        return requestBody;
    }

    private void prepareDb() {
        USER_ID = userRepository.save(UserEntityBuilder.defaultBuilder()
                .roles(List.of(UserRole.EMPLOYEE)).build()).getId();
        PLUGIN_ID = pluginRepository.save(PluginEntityBuilder.persistedDefaultBuilder()
                .ownerUser(userRepository.findById(USER_ID).get())
                .status(PluginStatus.ACTIVE)
                .description(EmbeddablePluginDescriptionBuilder.defaultBuilder()
                        .distributionMethods(List.of(DistributionMethodBuilder.defaultPurchaseDistribution())).build())
                .build()).getId();
        userForRequest = userRepository.findById(USER_ID).get();
    }
}
