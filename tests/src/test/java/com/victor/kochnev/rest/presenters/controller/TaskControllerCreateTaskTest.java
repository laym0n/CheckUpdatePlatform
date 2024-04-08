package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.api.dto.CreateTaskRequest;
import com.victor.kochnev.api.dto.DistributionMethodDto;
import com.victor.kochnev.api.dto.DistributionPlanTypeEnum;
import com.victor.kochnev.api.dto.PluginDescriptionDto;
import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescription;
import com.victor.kochnev.dal.entity.*;
import com.victor.kochnev.domain.enums.PluginStatus;
import com.victor.kochnev.domain.enums.TaskType;
import org.junit.jupiter.api.Test;
import org.springframework.test.web.servlet.MvcResult;

import java.math.BigDecimal;
import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class TaskControllerCreateTaskTest extends BaseControllerTest {
    private final String TASK_CREATE_ENDPOINT = "/task";
    private UUID USER_ID;
    private UUID PLUGIN_ID;
    private UserEntity userForRequest;

    @Test
    void createTaskForInitialize() {
        //Assign
        prepareDb();

        var requestBody = prepareRequestBody();

        //Action
        MvcResult mvcResult = post(TASK_CREATE_ENDPOINT, requestBody, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        PluginEntity pluginEntity = pluginRepository.findById(PLUGIN_ID).get();
        assertEquals(PluginStatus.CREATED, pluginEntity.getStatus());
        assertNull(pluginEntity.getDescription());

        List<TaskEntity> allTasks = taskRepository.findAllByPluginId(PLUGIN_ID);
        assertEquals(1, allTasks.size());
        TaskEntity taskEntity = allTasks.get(0);
        assertEquals(TaskType.INITIALIZE, taskEntity.getType());

        EmbeddablePluginDescription embeddableDescription = taskEntity.getDescription();
        assertNotNull(embeddableDescription);
        assertEquals(requestBody.getDescription().getDescription(), embeddableDescription.getDescription());
        assertEquals(requestBody.getDescription().getImagePaths(), embeddableDescription.getImagePaths());
        assertEquals(requestBody.getDescription().getTags(), embeddableDescription.getTags().getTags());
        assertEquals(requestBody.getDescription().getDistributionMethods().size(), embeddableDescription.getDistributionMethods().size());
        for (int i = 0; i < requestBody.getDescription().getDistributionMethods().size(); i++) {
            assertEqualsDistributionMethod(requestBody.getDescription().getDistributionMethods().get(i), embeddableDescription.getDistributionMethods().get(i));
        }
    }

    @Test
    void createTaskForSimpleUpdate() {
        //Assign
        prepareDb();
        PluginEntity pluginForSave = pluginRepository.findById(PLUGIN_ID).get();
        pluginForSave.setStatus(PluginStatus.ACTIVE);
        pluginRepository.save(pluginForSave);

        var requestBody = prepareRequestBody();

        //Action
        MvcResult mvcResult = post(TASK_CREATE_ENDPOINT, requestBody, prepareSimpleUserHeaders(userForRequest));

        //Assert
        assertHttpStatusOk(mvcResult);

        PluginEntity pluginEntity = pluginRepository.findById(PLUGIN_ID).get();
        assertEquals(PluginStatus.ACTIVE, pluginEntity.getStatus());
        assertNull(pluginEntity.getDescription());

        List<TaskEntity> allTasks = taskRepository.findAllByPluginId(PLUGIN_ID);
        assertEquals(1, allTasks.size());
        TaskEntity taskEntity = allTasks.get(0);
        assertEquals(TaskType.UPDATE, taskEntity.getType());

        EmbeddablePluginDescription embeddableDescription = taskEntity.getDescription();
        assertNotNull(embeddableDescription);
        assertEquals(requestBody.getDescription().getDescription(), embeddableDescription.getDescription());
        assertEquals(requestBody.getDescription().getImagePaths(), embeddableDescription.getImagePaths());
        assertEquals(requestBody.getDescription().getTags(), embeddableDescription.getTags().getTags());
        assertEquals(requestBody.getDescription().getDistributionMethods().size(), embeddableDescription.getDistributionMethods().size());
        for (int i = 0; i < requestBody.getDescription().getDistributionMethods().size(); i++) {
            assertEqualsDistributionMethod(requestBody.getDescription().getDistributionMethods().get(i), embeddableDescription.getDistributionMethods().get(i));
        }
    }

    private CreateTaskRequest prepareRequestBody() {
        var requestBody = new CreateTaskRequest();
        requestBody.setPluginId(PLUGIN_ID);

        var descriptionRequestBody = new PluginDescriptionDto();
        descriptionRequestBody.setDescription("decription");
        descriptionRequestBody.setImagePaths(List.of("first", "second"));
        descriptionRequestBody.setTags(List.of("tags1", "tags2"));
        descriptionRequestBody.setDistributionMethods(List.of(new DistributionMethodDto()
                        .type(DistributionPlanTypeEnum.PURCHASE)
                        .cost(BigDecimal.valueOf(1234)),
                new DistributionMethodDto()
                        .type(DistributionPlanTypeEnum.SUBSCRIBE)
                        .cost(BigDecimal.valueOf(4321))
                        .duration(Duration.of(5, ChronoUnit.DAYS).toString())));
        requestBody.setDescription(descriptionRequestBody);
        return requestBody;
    }

    private void assertEqualsDistributionMethod(DistributionMethodDto responseDistributionMethod,
                                                com.victor.kochnev.domain.value.object.DistributionMethod distributionMethod) {
        assertEquals(0, responseDistributionMethod.getCost().compareTo(distributionMethod.cost()));
        if (responseDistributionMethod.getDuration() != null && distributionMethod.duration() != null) {
            assertEquals(0, distributionMethod.duration().compareTo(Duration.parse(responseDistributionMethod.getDuration())));
        } else {
            assertEquals(responseDistributionMethod.getDuration(), distributionMethod.duration());
        }
        assertEquals(responseDistributionMethod.getType().name(), distributionMethod.type().name());
    }

    private void prepareDb() {
        USER_ID = userRepository.save(UserEntityBuilder.defaultBuilder().build()).getId();
        PLUGIN_ID = pluginRepository.save(PluginEntityBuilder.persistedDefaultBuilder()
                .ownerUser(userRepository.findById(USER_ID).get())
                .status(PluginStatus.CREATED)
                .description(null).build()).getId();
        userForRequest = userRepository.findById(USER_ID).get();
    }
}
