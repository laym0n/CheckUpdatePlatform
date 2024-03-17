package com.victor.kochnev.core.configuration;

import com.victor.kochnev.core.CoreBeanScanMarker;
import com.victor.kochnev.core.converter.DomainUserMapper;
import com.victor.kochnev.core.security.service.plugin.PluginSecurityService;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.domain.enums.UserRole;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.access.hierarchicalroles.RoleHierarchy;
import org.springframework.security.access.hierarchicalroles.RoleHierarchyImpl;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.access.expression.DefaultWebSecurityExpressionHandler;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

@Configuration
@EnableMethodSecurity
@ComponentScan(basePackageClasses = CoreBeanScanMarker.class)
public class CoreConfiguration {

    @Bean
    public ExecutorService executorService() {
        return Executors.newFixedThreadPool(4);
    }

    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }

    @Bean
    public RoleHierarchy roleHierarchy(DomainUserMapper mapper) {
        RoleHierarchyImpl roleHierarchy = new RoleHierarchyImpl();
        String simpleUserRole = mapper.mapToGrantedAuthority(UserRole.SIMPLE_USER).getAuthority();
        String employeeRole = mapper.mapToGrantedAuthority(UserRole.EMPLOYEE).getAuthority();
        String adminRole = mapper.mapToGrantedAuthority(UserRole.ADMIN).getAuthority();
        String hierarchy = String.format("%s > %s \n %s > %s", adminRole, employeeRole, employeeRole, simpleUserRole);
        roleHierarchy.setHierarchy(hierarchy);
        return roleHierarchy;
    }

    @Bean
    public DefaultWebSecurityExpressionHandler customWebSecurityExpressionHandler(RoleHierarchy roleHierarchy) {
        DefaultWebSecurityExpressionHandler expressionHandler = new DefaultWebSecurityExpressionHandler();
        expressionHandler.setRoleHierarchy(roleHierarchy);
        return expressionHandler;
    }

    @Bean
    public DaoAuthenticationProvider userAuthenticationProvider(SecurityUserService securityUserService, PasswordEncoder passwordEncoder) {
        DaoAuthenticationProvider userAuthenticationProvider = new DaoAuthenticationProvider();
        userAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        userAuthenticationProvider.setUserDetailsService(securityUserService);
        return userAuthenticationProvider;
    }

    @Bean
    public DaoAuthenticationProvider pluginAuthenticationProvider(PasswordEncoder passwordEncoder, PluginSecurityService pluginSecurityService) {
        DaoAuthenticationProvider userAuthenticationProvider = new DaoAuthenticationProvider();
        userAuthenticationProvider.setPasswordEncoder(passwordEncoder);
        userAuthenticationProvider.setUserDetailsService(pluginSecurityService);
        return userAuthenticationProvider;
    }

    @Bean
    public AuthenticationManager authenticationManager(List<AuthenticationProvider> authenticationProviderList) {
        return new ProviderManager(authenticationProviderList);
    }
}
