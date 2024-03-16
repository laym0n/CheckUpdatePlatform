package com.victor.kochnev.rest.presenters.security.service;

import com.victor.kochnev.core.dto.domain.entity.UserDto;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.facade.user.UserFacade;
import com.victor.kochnev.rest.presenters.converter.UserDtoMapper;
import com.victor.kochnev.rest.presenters.exception.RestPresentersException;
import com.victor.kochnev.rest.presenters.security.entity.UserSecurity;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserServiceImpl implements UserDetailsService, SecurityUserService {
    private final UserFacade userFacade;
    private final UserDtoMapper userDtoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userByEmail;
        try {
            userByEmail = userFacade.findUserByEmail(username);
        } catch (ResourceNotFoundException ex) {
            throw new UsernameNotFoundException(ex.getMessage());
        }
        return userDtoMapper.mapToSecurityUser(userByEmail);
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
        throw new RestPresentersException("Can not parse Authentication " + authentication + " to " + UserSecurity.class.getName());
    }
}
