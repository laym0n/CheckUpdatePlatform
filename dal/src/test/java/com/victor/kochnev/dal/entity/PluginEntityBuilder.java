package com.victor.kochnev.dal.entity;

import com.victor.kochnev.dal.embeddable.object.EmbeddablePluginDescription;
import com.victor.kochnev.domain.value.object.DistributionMethod;
import com.victor.kochnev.domain.value.object.DistributionMethodBuilder;

import java.time.ZonedDateTime;
import java.util.List;
import java.util.UUID;

public class PluginEntityBuilder {
    public static final String DEFAULT_BASE_URL = "http://localhost:9988";
    public static final String DEFAULT_ACCESS_TOKEN = "$2a$10$/1AcC1IGap3HYgXHsLCNkOXZybUL.vTc8mlY1mVk63NJwb2qh80Di";
    public static final String DEFAULT_NAME = "name";
    public static final List<String> DEFAULT_IMAGE_PATHS_LIST = List.of("example");
    public static final EmbeddablePluginDescription DEFAULT_DESCRIPTION = EmbeddablePluginDescription.builder().description("description").build();
    public static final List<DistributionMethod> DEFAULT_DISTRIBUTION_METHODS_COLLECTION = List.of(DistributionMethodBuilder.defaultPurchaseDistribution(), DistributionMethodBuilder.defaultSubscribeDistribution());

    private PluginEntityBuilder() {
    }

    public static PluginEntity.PluginEntityBuilder<?, ?> defaultBuilder() {
        return PluginEntity.builder()
                .name(DEFAULT_NAME)
                .baseUrl(DEFAULT_BASE_URL)
                .accessToken(DEFAULT_ACCESS_TOKEN)
                .imagePathsList(DEFAULT_IMAGE_PATHS_LIST)
                .description(DEFAULT_DESCRIPTION)
                .distributionMethodsCollection(DEFAULT_DISTRIBUTION_METHODS_COLLECTION)
                .ownerUser(UserEntityBuilder.defaultBuilder().build());
    }

    public static PluginEntity.PluginEntityBuilder<?, ?> persistedDefaultBuilder() {
        return defaultBuilder()
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }

    public static PluginEntity.PluginEntityBuilder<?, ?> postfixBuilder(int postfix) {
        return PluginEntity.builder()
                .name(DEFAULT_NAME + postfix)
                .baseUrl(DEFAULT_BASE_URL)
                .accessToken(DEFAULT_ACCESS_TOKEN)
                .imagePathsList(DEFAULT_IMAGE_PATHS_LIST)
                .description(DEFAULT_DESCRIPTION)
                .distributionMethodsCollection(DEFAULT_DISTRIBUTION_METHODS_COLLECTION)
                .ownerUser(UserEntityBuilder.defaultBuilder().build());
    }

    public static PluginEntity.PluginEntityBuilder<?, ?> persistedPostfixBuilder(int postfix) {
        return postfixBuilder(postfix)
                .id(UUID.randomUUID())
                .createDate(ZonedDateTime.now())
                .lastChangeDate(ZonedDateTime.now())
                .version(0L);
    }
}
