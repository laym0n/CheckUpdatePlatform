package com.victor.kochnev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication(scanBasePackages = "com.victor.kochnev.*")
public class CheckUpdatePlatformApp {
    public static void main(String[] args) {
        SpringApplication.run(CheckUpdatePlatformApp.class, args);
    }
}
