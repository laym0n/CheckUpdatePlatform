package com.victor.kochnev.rest.presenters.security;

import com.victor.kochnev.core.dto.UserDto;
import com.victor.kochnev.core.exception.ResourceNotFound;
import com.victor.kochnev.core.facade.user.UserFacade;
import com.victor.kochnev.rest.presenters.converter.UserDtoMapper;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SecurityUserService implements UserDetailsService {
    private final UserFacade userFacade;
    private final UserDtoMapper userDtoMapper;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        UserDto userByEmail;
        try {
            userByEmail = userFacade.findUserByEmail(username);
        } catch (ResourceNotFound ex) {
            throw new UsernameNotFoundException("User not found");
        }
        return userDtoMapper.mapToSecurityUser(userByEmail);
    }
}
