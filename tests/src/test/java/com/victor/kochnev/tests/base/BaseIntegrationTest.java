package com.victor.kochnev.tests.base;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.victor.kochnev.core.configuration.CoreConfiguration;
import com.victor.kochnev.dal.configuration.DalConfiguration;
import com.victor.kochnev.dal.repository.jpa.UserEntityRepository;
import com.victor.kochnev.integration.plugin.configuration.PluginIntegrationConfiguration;
import com.victor.kochnev.rest.presenters.configuration.RestPresentersConfiguration;
import com.victor.kochnev.rest.presenters.configuration.SecurityConfiguration;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Consumer;

@ActiveProfiles({"default", "production", "test"})
@SpringBootTest(classes = {
        CoreConfiguration.class,
        DalConfiguration.class,
        RestPresentersConfiguration.class,
        SecurityConfiguration.class,
        PluginIntegrationConfiguration.class})
@Testcontainers
@Sql("classpath:initdata/clean_db.sql")
public abstract class BaseIntegrationTest {

    @ServiceConnection
    public static final PostgreSQLContainer postgreSQLContainer = (PostgreSQLContainer) new PostgreSQLContainer(DockerImageName.parse("postgres:15"))
            .withCreateContainerCmdModifier((Consumer<CreateContainerCmd>) cmd -> cmd
                    .withHostConfig(
                            new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(65419), new ExposedPort(5432)))
                    ));

    static {
        postgreSQLContainer.start();
    }

    @Autowired
    protected UserEntityRepository userEntityRepository;

}
