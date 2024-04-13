package com.victor.kochnev.dal.entity;

import com.victor.kochnev.core.repository.PluginUsageRepository;
import com.victor.kochnev.dal.BaseBootTest;
import com.victor.kochnev.dal.embeddable.object.EmbeddableDistributionMethodBuilder;
import com.victor.kochnev.domain.entity.PluginUsage;
import com.victor.kochnev.domain.enums.DistributionPlanType;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class PluginUsageRepositoryTest extends BaseBootTest {
    private UUID USER_ID;
    private UUID PLUGIN_ID;
    @Autowired
    private PluginUsageRepository pluginUsageRepository;

    @Test
    void testFindPluginUsageWithExpiredDateAfterOrNull() {
        //Assign
        UUID pluginUsageId1 = pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(USER_ID).get())
                .plugin(pluginEntityRepository.findById(PLUGIN_ID).get())
                .expiredDate(null)
                .distributionMethod(EmbeddableDistributionMethodBuilder.defaultPurchaseDistribution().build()).build()).getId();

        ZonedDateTime expectedExpiredDate = ZonedDateTime.now().plusMinutes(5);
        UUID pluginUsageId2 = pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(USER_ID).get())
                .plugin(pluginEntityRepository.findById(PLUGIN_ID).get())
                .expiredDate(expectedExpiredDate)
                .distributionMethod(EmbeddableDistributionMethodBuilder.defaultPurchaseDistribution().build()).build()).getId();

        //Action
        List<PluginUsage> actualList = pluginUsageRepository.findPluginUsageWithExpiredDateAfterOrNull(USER_ID, PLUGIN_ID, ZonedDateTime.now());

        //Assert
        assertNotNull(actualList);
        assertEquals(2, actualList.size());

        PluginUsage pluginUsage1 = actualList.stream().filter(usage -> usage.getId().equals(pluginUsageId1)).findFirst().get();
        assertNotNull(pluginUsage1);
        assertEquals(pluginUsageId1, pluginUsage1.getId());
        assertEquals(DistributionPlanType.PURCHASE, pluginUsage1.getDistributionMethod().getType());
        assertNull(pluginUsage1.getDistributionMethod().getDuration());
        assertEquals(0, EmbeddableDistributionMethodBuilder.DEFAULT_COST.compareTo(pluginUsage1.getDistributionMethod().getCost()));
        assertNull(pluginUsage1.getExpiredDate());

        PluginUsage pluginUsage2 = actualList.stream().filter(usage -> usage.getId().equals(pluginUsageId2)).findFirst().get();
        assertNotNull(pluginUsage2);
        assertTrue(expectedExpiredDate.truncatedTo(ChronoUnit.MINUTES).isEqual(pluginUsage2.getExpiredDate().truncatedTo(ChronoUnit.MINUTES)));
    }

    @Test
    void testFindPluginUsageWithExpiredDateAfterOrNull_WhenNotExists_ExpectEmptyList() {
        //Assign
        pluginUsageEntityRepository.save(PluginUsageEntityBuilder.persistedDefaultBuilder()
                .user(userEntityRepository.findById(USER_ID).get())
                .plugin(pluginEntityRepository.findById(PLUGIN_ID).get())
                .expiredDate(ZonedDateTime.now().minusMinutes(5))
                .distributionMethod(EmbeddableDistributionMethodBuilder.defaultPurchaseDistribution().build()).build()).getId();

        //Action
        List<PluginUsage> actualList = pluginUsageRepository.findPluginUsageWithExpiredDateAfterOrNull(USER_ID, PLUGIN_ID, ZonedDateTime.now());

        //Assert
        assertNotNull(actualList);
        assertTrue(actualList.isEmpty());
    }

    @BeforeEach
    public void prepareDb() {
        USER_ID = userEntityRepository.save(UserEntityBuilder.persistedDefaultBuilder().build()).getId();
        PLUGIN_ID = pluginEntityRepository.save(PluginEntityBuilder.persistedDefaultBuilder()
                .ownerUser(userEntityRepository.findById(USER_ID).get()).build()).getId();
    }
}
