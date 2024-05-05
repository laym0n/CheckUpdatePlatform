package com.victor.kochnev.rest.presenters.configuration;

import com.victor.kochnev.domain.enums.UserRole;
import com.victor.kochnev.rest.presenters.security.filter.JwtAuthenticationFilter;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.access.expression.DefaultHttpSecurityExpressionHandler;
import org.springframework.security.web.access.expression.WebExpressionAuthorizationManager;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtConfigurationProperties.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain restPresentersSecurityFilterChain(HttpSecurity http, JwtAuthenticationFilter filter, ApplicationContext context) throws Exception {
        var expressionHandler = new DefaultHttpSecurityExpressionHandler();
        expressionHandler.setApplicationContext(context);
        var observingAuthorization = new WebExpressionAuthorizationManager("@authorizationHelper.checkWebResourceObservingAccess(authentication, #id)");
        observingAuthorization.setExpressionHandler(expressionHandler);

        return http
                .securityMatcher("/webresource/observing/**", "/user/register", "/authentication/**", "/plugin/**", "/task/**", "/autocomplete/**", "/task*")
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/task/{id}/decision").hasRole(UserRole.EMPLOYEE.name())
                        .requestMatchers("/task/{id}/creator/decision").hasRole(UserRole.SIMPLE_USER.name())
                        .requestMatchers("/webresource/observing/{id}/**").access(observingAuthorization)
                        .requestMatchers("/webresource/observing/**", "/task**").hasRole(UserRole.SIMPLE_USER.name())
                        .requestMatchers(HttpMethod.POST, "/plugin/**").hasRole(UserRole.SIMPLE_USER.name())
                        .requestMatchers(HttpMethod.PUT, "/plugin/**").hasRole(UserRole.SIMPLE_USER.name())
                        .requestMatchers(HttpMethod.GET, "/plugin/usage").hasRole(UserRole.SIMPLE_USER.name())
                        .requestMatchers(HttpMethod.GET, "/plugin/my", "/plugin/own").hasRole(UserRole.SIMPLE_USER.name())
                        .requestMatchers(HttpMethod.GET, "/plugin").permitAll()
                        .requestMatchers("/user/register", "/authentication/**", "/autocomplete/**").permitAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
                .cors(Customizer.withDefaults())
                .exceptionHandling(configurer -> configurer.authenticationEntryPoint(new HttpStatusEntryPoint(HttpStatus.UNAUTHORIZED)))
                .sessionManagement(configurer -> configurer.sessionCreationPolicy(SessionCreationPolicy.STATELESS))
                .addFilterBefore(filter, UsernamePasswordAuthenticationFilter.class)
                .build();
    }

    @Bean
    @Order(Ordered.LOWEST_PRECEDENCE)
    public SecurityFilterChain denyAllSecurityFilterChain(HttpSecurity http) throws Exception {
        return http
                .securityMatcher("/**")
                .cors(Customizer.withDefaults())
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().denyAll()
                ).build();
    }
}
