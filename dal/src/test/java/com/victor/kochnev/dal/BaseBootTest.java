package com.victor.kochnev.dal;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import com.victor.kochnev.dal.configuration.DalConfiguration;
import com.victor.kochnev.dal.repository.jpa.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.jdbc.Sql;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.junit.jupiter.Testcontainers;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Consumer;

@SpringBootTest(classes = DalConfiguration.class)
@ActiveProfiles({"default", "production", "test"})
@Testcontainers
@Sql("classpath:initdata/clean_db.sql")
public abstract class BaseBootTest {

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
    @Autowired
    protected PluginEntityRepository pluginEntityRepository;
    @Autowired
    protected PluginUsageEntityRepository pluginUsageEntityRepository;
    @Autowired
    protected WebResourceEntityRepository webResourceEntityRepository;
    @Autowired
    protected WebResourceObservingEntityRepository webResourceObservingEntityRepository;
    @Autowired
    protected NotificationEntityRepository notificationEntityRepository;
    @Autowired
    protected TaskEntityRepository taskEntityRepository;
    @Autowired
    protected FeedbackEntityRepository feedbackEntityRepository;
}
