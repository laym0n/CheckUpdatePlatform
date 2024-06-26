package com.victor.kochnev.application;

import com.victor.kochnev.application.configuration.LocalDevTestcontainersConfiguration;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CheckUpdatePlatformLocalDevApp {
    public static void main(String[] args) {
        SpringApplication.from(CheckUpdatePlatformApp::main)
                .with(LocalDevTestcontainersConfiguration.class)
                .run(args);
    }
}
