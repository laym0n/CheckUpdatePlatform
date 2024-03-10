package com.victor.kochnev.rest.presenters.security.filter;

import com.victor.kochnev.rest.presenters.security.service.JwtService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
@Slf4j
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private static final String BEARER_PREFIX = "Bearer ";
    private static final String AUTHORIZATION_HEADER = "Authorization";
    private final JwtService jwtService;
    @Qualifier("securityUserServiceImpl")
    private final UserDetailsService userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        if (SecurityContextHolder.getContext().getAuthentication() == null) {
            tryAuthenticate(request);
        }
        filterChain.doFilter(request, response);
    }

    private void tryAuthenticate(HttpServletRequest request) {
        String requestHeader = request.getHeader(AUTHORIZATION_HEADER);
        String username = null;
        if (requestHeader != null && requestHeader.startsWith(BEARER_PREFIX)) {
            String token = requestHeader.substring(BEARER_PREFIX.length());
            try {
                username = this.jwtService.getUsernameFromToken(token);
            } catch (Exception e) {
                log.error(ExceptionUtils.getMessage(e));
            }
        }
        if (username != null) {
            UserDetails userDetails;
            try {
                userDetails = this.userDetailsService.loadUserByUsername(username);
            } catch (Exception ex) {
                log.error(ExceptionUtils.getMessage(ex), ex);
                return;
            }
            UsernamePasswordAuthenticationToken authentication = new UsernamePasswordAuthenticationToken(userDetails, null, userDetails.getAuthorities());
            SecurityContextHolder.getContext().setAuthentication(authentication);
        }
    }
}
