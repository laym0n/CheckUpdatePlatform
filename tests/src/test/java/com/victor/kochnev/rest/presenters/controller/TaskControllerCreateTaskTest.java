package com.victor.kochnev.rest.presenters.controller;

import com.victor.kochnev.BaseControllerTest;
import com.victor.kochnev.core.dto.domain.value.object.DistributionMethodDto;
import com.victor.kochnev.core.dto.domain.value.object.PluginDescriptionDto;
import com.victor.kochnev.core.dto.domain.value.object.PluginSpecificDescriptionDto;
import com.victor.kochnev.core.dto.request.CreateTaskRequestDto;
import com.victor.kochnev.dal.entity.*;
import com.victor.kochnev.domain.enums.DistributionPlanType;
import com.victor.kochnev.domain.enums.PluginStatus;
import com.victor.kochnev.domain.enums.TaskType;
import com.victor.kochnev.domain.value.object.DistributionMethod;
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

        var embeddableDescription = taskEntity.getDescription();
        assertNotNull(embeddableDescription);
        assertEquals(requestBody.getDescription().getSpecificDescription().getDescription(), embeddableDescription.getSpecificDescription().getDescription());
        assertEquals(requestBody.getDescription().getSpecificDescription().getImagePaths(), embeddableDescription.getSpecificDescription().getImagePaths());
        assertEquals(requestBody.getDescription().getSpecificDescription().getTags(), embeddableDescription.getSpecificDescription().getTags().getTags());
        assertEquals(requestBody.getDescription().getLogoPath(), embeddableDescription.getLogoPath());
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

        var embeddableDescription = taskEntity.getDescription();
        assertNotNull(embeddableDescription);
        assertEquals(requestBody.getDescription().getSpecificDescription().getDescription(), embeddableDescription.getSpecificDescription().getDescription());
        assertEquals(requestBody.getDescription().getSpecificDescription().getImagePaths(), embeddableDescription.getSpecificDescription().getImagePaths());
        assertEquals(requestBody.getDescription().getSpecificDescription().getTags(), embeddableDescription.getSpecificDescription().getTags().getTags());
        assertEquals(requestBody.getDescription().getLogoPath(), embeddableDescription.getLogoPath());
        assertEquals(requestBody.getDescription().getDistributionMethods().size(), embeddableDescription.getDistributionMethods().size());
        for (int i = 0; i < requestBody.getDescription().getDistributionMethods().size(); i++) {
            assertEqualsDistributionMethod(requestBody.getDescription().getDistributionMethods().get(i), embeddableDescription.getDistributionMethods().get(i));
        }
    }

    private CreateTaskRequestDto prepareRequestBody() {
        var requestBody = new CreateTaskRequestDto();
        requestBody.setPluginId(PLUGIN_ID);

        var specificDescriptionDto = new PluginSpecificDescriptionDto();
        specificDescriptionDto.setDescription("decription");
        specificDescriptionDto.setImagePaths(List.of("first", "second"));
        specificDescriptionDto.setTags(List.of("tags1", "tags2"));

        var pluginDescriptionDto = new PluginDescriptionDto();
        pluginDescriptionDto.setDistributionMethods(
                List.of(
                        new DistributionMethodDto(DistributionPlanType.PURCHASE,
                                null,
                                BigDecimal.valueOf(1234)),
                        new DistributionMethodDto(
                                DistributionPlanType.SUBSCRIBE,
                                Duration.of(5, ChronoUnit.DAYS),
                                BigDecimal.valueOf(4321))
                )
        );
        pluginDescriptionDto.setSpecificDescription(specificDescriptionDto);
        pluginDescriptionDto.setLogoPath("logoPath");

        requestBody.setDescription(pluginDescriptionDto);
        return requestBody;
    }

    private void assertEqualsDistributionMethod(DistributionMethodDto responseDistributionMethod, DistributionMethod distributionMethod) {
        assertEquals(0, responseDistributionMethod.getCost().compareTo(distributionMethod.getCost()));
        if (responseDistributionMethod.getDuration() != null && distributionMethod.getDuration() != null) {
            assertEquals(0, distributionMethod.getDuration().compareTo(responseDistributionMethod.getDuration()));
        } else {
            assertEquals(responseDistributionMethod.getDuration(), distributionMethod.getDuration());
        }
        assertEquals(responseDistributionMethod.getType().name(), distributionMethod.getType().name());
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
