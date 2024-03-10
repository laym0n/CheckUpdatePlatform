package com.victor.kochnev.application.configuration;

import com.github.dockerjava.api.command.CreateContainerCmd;
import com.github.dockerjava.api.model.ExposedPort;
import com.github.dockerjava.api.model.HostConfig;
import com.github.dockerjava.api.model.PortBinding;
import com.github.dockerjava.api.model.Ports;
import org.springframework.boot.test.context.TestConfiguration;
import org.springframework.boot.testcontainers.service.connection.ServiceConnection;
import org.springframework.context.annotation.Bean;
import org.testcontainers.containers.PostgreSQLContainer;
import org.testcontainers.utility.DockerImageName;

import java.util.function.Consumer;

@TestConfiguration(proxyBeanMethods = false)
public class LocalDevTestcontainersConfiguration {
    @Bean
    @ServiceConnection
    public PostgreSQLContainer mongoDBContainer() {
        return (PostgreSQLContainer) new PostgreSQLContainer(DockerImageName.parse("postgres:15"))
                .withCreateContainerCmdModifier((Consumer<CreateContainerCmd>) cmd -> cmd
                        .withHostConfig(
                                new HostConfig().withPortBindings(new PortBinding(Ports.Binding.bindPort(65419), new ExposedPort(5432)))
                        ));
    }
}
