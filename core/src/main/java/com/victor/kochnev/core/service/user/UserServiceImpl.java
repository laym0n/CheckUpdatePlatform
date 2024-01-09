package com.victor.kochnev.core.service.user;

import com.victor.kochnev.core.converter.DomainUserMapper;
import com.victor.kochnev.core.dto.UserRegistrationRequestDto;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.domain.entity.User;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.security.InvalidParameterException;
import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DomainUserMapper domainUserMapper;

    @Override
    public void createUser(UserRegistrationRequestDto request) {
        Optional<User> optionalUser = userRepository.findUserByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            throw new InvalidParameterException();
        }

        log.info("Create new User {}", request.getEmail());
        User newUser = domainUserMapper.mapToEntity(request);
        userRepository.create(newUser);
    }
}
