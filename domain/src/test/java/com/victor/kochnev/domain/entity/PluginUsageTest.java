package com.victor.kochnev.domain.entity;

import com.victor.kochnev.domain.entity.builder.PluginUsageDomainBuilder;
import com.victor.kochnev.domain.value.object.DistributionMethodBuilder;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.extension.ExtendWith;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Duration;
import java.time.ZonedDateTime;
import java.time.temporal.ChronoUnit;
import java.util.stream.Stream;

@ExtendWith(MockitoExtension.class)
class PluginUsageTest {

    @ParameterizedTest
    @ArgumentsSource(CanUseTestArgumentProvider.class)
    void canUseTest(PluginUsage pluginUsage, ZonedDateTime when, Boolean expectedResult) {
        //Action
        boolean actualResult = pluginUsage.canUse(when);

        //Assert
        Assertions.assertEquals(expectedResult, actualResult);
    }

    private static class CanUseTestArgumentProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            ZonedDateTime now = ZonedDateTime.now();
            Duration subscribeDuration = Duration.of(10, ChronoUnit.DAYS);
            PluginUsage subscribePluginUsage = PluginUsageDomainBuilder.persistedDefaultUser()
                    .createDate(now)
                    .distributionMethod(DistributionMethodBuilder.subscribeDistribution(subscribeDuration))
                    .build();
            PluginUsage purchasePluginUsage = PluginUsageDomainBuilder.persistedDefaultUser()
                    .createDate(now)
                    .distributionMethod(DistributionMethodBuilder.defaultPurchaseDistribution())
                    .build();
            return Stream.of(
                    Arguments.of(subscribePluginUsage, now, true),
                    Arguments.of(subscribePluginUsage, now.plus(subscribeDuration), true),
                    Arguments.of(subscribePluginUsage, now
                            .plus(subscribeDuration)
                            .minus(1, ChronoUnit.MINUTES), true),
                    Arguments.of(subscribePluginUsage, now.plus(1, ChronoUnit.MINUTES), true),
                    Arguments.of(subscribePluginUsage, now.minus(1, ChronoUnit.MINUTES), false),
                    Arguments.of(subscribePluginUsage, now
                            .plus(subscribeDuration)
                            .plus(1, ChronoUnit.MINUTES), false),
                    Arguments.of(purchasePluginUsage, now.minus(1, ChronoUnit.MINUTES), false),
                    Arguments.of(purchasePluginUsage, now, true),
                    Arguments.of(purchasePluginUsage, now.plus(1, ChronoUnit.MINUTES), true),
                    Arguments.of(purchasePluginUsage, now.plus(1, ChronoUnit.YEARS), true)
            );
        }
    }
}
