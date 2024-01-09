package com.victor.kochnev.dal.configuration;

import com.victor.kochnev.dal.DalBeanScanMarker;
import com.victor.kochnev.dal.entity.EntityScanMarker;
import com.victor.kochnev.dal.repository.jpa.JpaRepositoryScanMarker;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@Configuration
@ComponentScan(basePackageClasses = DalBeanScanMarker.class)
@EnableJpaRepositories(basePackageClasses = JpaRepositoryScanMarker.class)
@EntityScan(basePackageClasses = EntityScanMarker.class)
@EnableTransactionManagement
@EnableAutoConfiguration
public class DalConfiguration {
}
