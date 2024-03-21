package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.dal.BaseBootTest;
import com.victor.kochnev.dal.embeddable.object.EmbeddableDistributionMethod;
import com.victor.kochnev.dal.entity.PluginEntityBuilder;
import com.victor.kochnev.dal.entity.PluginUsageEntityBuilder;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.domain.entity.PluginUsage;
import com.victor.kochnev.domain.enums.DistributionPlanType;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.math.BigDecimal;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertNotNull;

class PluginUsageRepositoryTest extends BaseBootTest {
    @Autowired
    private PluginUsageRepository pluginUsageRepository;

    @Test
    void testSuccessFindPluginUsageWithExpiredDateAfterOrNull() {
        //Assign
        UUID userId1 = userEntityRepository.save(UserEntityBuilder.persistedPostfixBuilder(0).build()).getId();
        UUID userId2 = userEntityRepository.save(UserEntityBuilder.persistedPostfixBuilder(1).build()).getId();
        UUID pluginId = pluginEntityRepository.save(PluginEntityBuilder.persistedPostfixBuilder(1)
                .ownerUser(userEntityRepository.findById(userId1).get()).build()).getId();

        UUID pluginUsageId1 = pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(0)
                .user(userEntityRepository.findById(userId1).get())
                .plugin(pluginEntityRepository.findById(pluginId).get())
                .expiredDate(null)
                .distributionMethod(EmbeddableDistributionMethod.builder()
                        .type(DistributionPlanType.PURCHASE)
                        .duration(null)
                        .cost(BigDecimal.TEN)
                        .build())
                .build()).getId();
        UUID pluginUsageId2 = pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedPostfixBuilder(0)
                .user(userEntityRepository.findById(userId2).get())
                .plugin(pluginEntityRepository.findById(pluginId).get())
                .expiredDate(null)
                .distributionMethod(EmbeddableDistributionMethod.builder()
                        .type(DistributionPlanType.PURCHASE)
                        .duration(null)
                        .cost(BigDecimal.TEN)
                        .build())
                .build()).getId();

        //Action
        List<PluginUsage> pluginUsages = pluginUsageRepository.findPluginUsageWithExpiredDateAfterOrNull(userId1, pluginId, null);

        //Assert
        assertNotNull(pluginUsages);
        assertEquals(1, pluginUsages.size());

        PluginUsage pluginUsage = pluginUsages.get(0);
        assertEquals(pluginUsageId1, pluginUsage.getId());
    }
}
