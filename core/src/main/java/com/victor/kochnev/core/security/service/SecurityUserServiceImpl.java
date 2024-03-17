package com.victor.kochnev.core.security.service;

import com.victor.kochnev.core.converter.DomainUserMapper;
import com.victor.kochnev.core.exception.CoreException;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.core.service.user.UserService;
import com.victor.kochnev.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements SecurityUserService {
    private final UserService userService;
    private final DomainUserMapper userMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        User userByEmail;
        try {
            userByEmail = userService.findUserByEmail(username);
        } catch (ResourceNotFoundException ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
        return userMapper.mapToSecurityUser(userByEmail);
    }

    @Override
    public UserSecurity getCurrentUser() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return (UserSecurity) authentication.getPrincipal();
    }

    @Override
    public UserSecurity getUserFromAuthentication(Authentication authentication) {
        if (authentication.getPrincipal() instanceof UserSecurity userSecurity) {
            return userSecurity;
        }
        throw new CoreException("Can not parse Authentication " + authentication + " to " + UserSecurity.class.getName());
    }
}
