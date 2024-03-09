package com.victor.kochnev.rest.presenters.configuration;

import com.victor.kochnev.core.service.user.PasswordCoder;
import com.victor.kochnev.domain.enums.UserRole;
import com.victor.kochnev.rest.presenters.security.filter.JwtAuthenticationFilter;
import com.victor.kochnev.rest.presenters.security.service.SecurityUserServiceImpl;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.Ordered;
import org.springframework.core.annotation.Order;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.Customizer;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.authentication.HttpStatusEntryPoint;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;

import java.util.List;

@Configuration
@EnableWebSecurity
@EnableConfigurationProperties(JwtConfigurationProperties.class)
@Order(Ordered.HIGHEST_PRECEDENCE)
public class SecurityConfiguration {

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SecurityFilterChain restPresentersSecurityFilterChain(HttpSecurity http, JwtAuthenticationFilter filter) throws Exception {
        return http
                .authorizeHttpRequests(auth -> auth
                        .requestMatchers("/webresource/observing/**").hasRole(UserRole.SIMPLE_USER.name())
                        .requestMatchers("/user/register", "/authentication").permitAll()
                        .anyRequest().denyAll()
                )
                .httpBasic(AbstractHttpConfigurer::disable)
                .formLogin(AbstractHttpConfigurer::disable)
                .csrf(AbstractHttpConfigurer::disable)
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
                .authorizeHttpRequests(auth -> auth
                        .anyRequest().denyAll()
                ).build();
    }

    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {
        return http.authorizeHttpRequests(request -> request.anyRequest()
                        .authenticated())
                .httpBasic(Customizer.withDefaults())
                .build();
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public PasswordCoder passwordcoder(PasswordEncoder passwordEncoder) {
        return passwordEncoder::encode;
    }

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider(SecurityUserServiceImpl securityUserServiceImpl) {
        DaoAuthenticationProvider userAuthenticationProvider = new DaoAuthenticationProvider();
        userAuthenticationProvider.setPasswordEncoder(passwordEncoder());
        userAuthenticationProvider.setUserDetailsService(securityUserServiceImpl);
        return userAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(List<AuthenticationProvider> authenticationProviderList) throws Exception {
        return new ProviderManager(authenticationProviderList);
    }
}
