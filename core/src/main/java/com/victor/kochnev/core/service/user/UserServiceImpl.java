package com.victor.kochnev.core.service.user;

import com.victor.kochnev.core.converter.DomainUserMapper;
import com.victor.kochnev.core.dto.domain.UserDto;
import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import com.victor.kochnev.core.exception.ResourceNotFound;
import com.victor.kochnev.core.exception.UserRegistrationException;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.enums.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DomainUserMapper domainUserMapper;
    private final PasswordCoder passwordCoder;

    @Override
    public void createUser(UserRegistrationRequestDto request) {
        Optional<User> optionalUser = userRepository.findUserByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserRegistrationException(request.getEmail());
        }

        log.info("Create new User {}", request.getEmail());
        User newUser = domainUserMapper.mapToEntity(request);
        String encodedPassword = passwordCoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.addRole(UserRole.SIMPLE_USER);
        userRepository.create(newUser);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        return optionalUser.map(domainUserMapper::mapToUserDto).orElseThrow(() -> ResourceNotFound.create(User.class, email, "email"));
    }

    @Override
    public List<User> findAllObserversOfWebResource(UUID pluginId, String webResourceName) {
        return userRepository.findAllObserversOfWebResource(pluginId, webResourceName);
    }
}
