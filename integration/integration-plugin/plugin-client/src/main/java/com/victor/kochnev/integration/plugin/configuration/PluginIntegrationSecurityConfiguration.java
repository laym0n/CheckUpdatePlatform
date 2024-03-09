package com.victor.kochnev.integration.plugin.configuration;

import com.victor.kochnev.integration.plugin.security.entity.PluginAuthority;
import com.victor.kochnev.integration.plugin.security.service.SecurityPluginService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;

@Configuration
@EnableWebSecurity
public class PluginIntegrationSecurityConfiguration {
    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE - 1)
    public SecurityFilterChain pluginSecurityFilterChain(HttpSecurity http) throws Exception {
        http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/notification/create").hasAuthority(PluginAuthority.PLUGIN_AUTHORITY.getAuthority())
                        .anyRequest().denyAll()
                )
                .httpBasic(Customizer.withDefaults())
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS));
        return http.build();
    }

    @Bean
    public DaoAuthenticationProvider pluginAuthenticationProvider(PasswordEncoder passwordEncoder, SecurityPluginService securityPluginService) {
        DaoAuthenticationProvider userAuthenticationProvider = new DaoAuthenticationProvider();
        userAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        userAuthenticationProvider.setUserDetailsService(securityPluginService);
        return userAuthenticationProvider;
    }
}
