package com.victor.kochnev;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.github.tomakehurst.wiremock.WireMockServer;
import com.github.tomakehurst.wiremock.client.ResponseDefinitionBuilder;
import com.github.tomakehurst.wiremock.client.WireMock;
import com.victor.kochnev.core.configuration.CoreConfiguration;
import com.victor.kochnev.dal.configuration.DalConfiguration;
import com.victor.kochnev.dal.repository.jpa.*;
import com.victor.kochnev.integration.mail.config.MailIntegrationConfiguration;
import com.victor.kochnev.integration.plugin.configuration.PluginIntegrationConfiguration;
import com.victor.kochnev.integration.telegram.config.TelegramIntegrationConfiguration;
import com.victor.kochnev.rest.presenters.configuration.RestPresentersConfiguration;
import lombok.SneakyThrows;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Consumer;

@ActiveProfiles({"default", "test"})
@SpringBootTest(classes = {
        CoreConfiguration.class,
        DalConfiguration.class,
        RestPresentersConfiguration.class,
        PluginIntegrationConfiguration.class,
        MailIntegrationConfiguration.class,
        TelegramIntegrationConfiguration.class})
@Testcontainers
@Sql("classpath:initdata/clean_db.sql")
public abstract class BaseBootTest {
    public static final int WIRE_MOCK_PORT = 9988;

    @ServiceConnection
    public static final PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer(DockerImageName.parse("postgres:15"))
            .withCreateContainerCmdModifier((Consumer<CreateContainerCmd>) cmd -> cmd
                    .withHostConfig(
                            new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(65419), new ExposedPort(5432)))
                    ));
    private static ObjectMapper objectMapper = new ObjectMapper()
            .registerModule(new JavaTimeModule())
            .configure(DeserializationFeature.FAIL_ON_NUMBERS_FOR_ENUMS, true)
            .configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);

    static {
        postgreSQLContainer.start();
    }

    @Autowired
    protected UserEntityRepository userRepository;
    @Autowired
    protected PluginEntityRepository pluginRepository;
    @Autowired
    protected PluginUsageEntityRepository pluginUsageRepository;
    @Autowired
    protected WebResourceEntityRepository webResourceRepository;
    @Autowired
    protected WebResourceObservingEntityRepository observingRepository;
    @Autowired
    protected NotificationEntityRepository notificationRepository;
    protected WireMockServer wireMockServer;

    protected static ResponseDefinitionBuilder wireMockResponseJson(String resourceJson) {
        return WireMock.aResponse().withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).withBodyFile(resourceJson);
    }

    @SneakyThrows
    protected static ResponseDefinitionBuilder wireMockResponse(Object body) {
        return WireMock.aResponse().withHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE).withBody(objectMapper.writeValueAsString(body));
    }

    @BeforeEach
    public void startWiremock() {
        wireMockServer = new WireMockServer(WIRE_MOCK_PORT);
        wireMockServer.start();
    }

    @AfterEach
    public void stopWiremock() {
        if (wireMockServer != null) {
            wireMockServer.stop();
        }
    }
}
