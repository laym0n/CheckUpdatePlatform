package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.dto.dal.GetPluginUsagesDalRequestDto;
import com.victor.kochnev.core.dto.dal.PluginUsagesFilterDalDto;
import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.dal.BaseBootTest;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.PluginUsageEntityBuilder;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.domain.entity.PluginUsage;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class GetByFiltersPluginUsageRepositoryTest extends BaseBootTest {
    private static UUID OWNER_ID;
    private static UUID USER_ID1;
    private static UUID USER_ID2;
    private static UUID PLUGIN_ID1;
    private static UUID PLUGIN_ID2;
    private static UUID PLUGIN_USAGE_ID1;
    private static UUID PLUGIN_USAGE_ID2;
    private static UUID PLUGIN_USAGE_ID3;
    private static UUID PLUGIN_USAGE_ID4;
    @Autowired
    private PluginUsageRepository pluginUsageRepository;

    @Test
    void testGetByFilters_GetByUserIds() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setUserIds(List.of(USER_ID1));

        //Action
        var response = pluginUsageRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertNotNull(response.getPluginUsages());
        assertEquals(2, response.getPluginUsages().size());

        assertContains(response.getPluginUsages(), PLUGIN_USAGE_ID1);
        assertContains(response.getPluginUsages(), PLUGIN_USAGE_ID2);
    }

    @Test
    void testGetByFilters_GetByPluginIds() {
        //Assign
        prepareDb();
        var request = prepareRequest();
        request.getFilters().setPluginIds(List.of(PLUGIN_ID1));

        //Action
        var response = pluginUsageRepository.getByFilters(request);

        //Assert
        assertNotNull(response);
        assertNotNull(response.getPluginUsages());
        assertEquals(2, response.getPluginUsages().size());

        assertContains(response.getPluginUsages(), PLUGIN_USAGE_ID1);
        assertContains(response.getPluginUsages(), PLUGIN_USAGE_ID3);
    }

    private GetPluginUsagesDalRequestDto prepareRequest() {
        var requestDto = new GetPluginUsagesDalRequestDto();
        requestDto.setFilters(new PluginUsagesFilterDalDto());
        return requestDto;
    }

    private void assertContains(List<PluginUsage> pluginUsages, UUID id) {
        assertTrue(pluginUsages.stream().anyMatch(usage -> usage.getId().equals(id)));
    }

    void prepareDb() {
        OWNER_ID = userEntityRepository.save(UserEntityBuilder.persistedPostfixBuilder(1).build()).getId();
        USER_ID1 = userEntityRepository.save(UserEntityBuilder.persistedPostfixBuilder(2).build()).getId();
        USER_ID2 = userEntityRepository.save(UserEntityBuilder.persistedPostfixBuilder(3).build()).getId();

        PLUGIN_ID1 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .ownerUser(userEntityRepository.findById(OWNER_ID).get())
                .build()).getId();
        PLUGIN_ID2 = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(2)
                .ownerUser(userEntityRepository.findById(OWNER_ID).get())
                .build()).getId();

        PLUGIN_USAGE_ID1 = pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
                .user(userEntityRepository.findById(USER_ID1).get())
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        PLUGIN_USAGE_ID2 = pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
                .user(userEntityRepository.findById(USER_ID1).get())
                .plugin(pluginEntityRepository.findById(PLUGIN_ID2).get())
                .build()).getId();
        PLUGIN_USAGE_ID3 = pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
                .user(userEntityRepository.findById(USER_ID2).get())
                .plugin(pluginEntityRepository.findById(PLUGIN_ID1).get())
                .build()).getId();
        PLUGIN_USAGE_ID4 = pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(1)
                .user(userEntityRepository.findById(USER_ID2).get())
                .plugin(pluginEntityRepository.findById(PLUGIN_ID2).get())
                .build()).getId();
    }
}
