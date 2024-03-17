package com.victor.kochnev.core.service.user;

import com.victor.kochnev.core.converter.DomainUserMapper;
import com.victor.kochnev.core.dto.domain.entity.UserDto;
import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.exception.UserRegistrationException;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.enums.UserRole;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
@Slf4j
public class UserServiceImpl implements UserService {
    private final UserRepository userRepository;
    private final DomainUserMapper domainUserMapper;
    private final PasswordEncoder passwordEncoder;

    @Override
    public void createUser(UserRegistrationRequestDto request) {
        Optional<User> optionalUser = userRepository.findUserByEmail(request.getEmail());
        if (optionalUser.isPresent()) {
            throw new UserRegistrationException(request.getEmail());
        }

        log.info("Create new User {}", request.getEmail());
        User newUser = domainUserMapper.mapToEntity(request);
        String encodedPassword = passwordEncoder.encode(newUser.getPassword());
        newUser.setPassword(encodedPassword);
        newUser.addRole(UserRole.SIMPLE_USER);
        userRepository.create(newUser);
    }

    @Override
    public UserDto findUserByEmail(String email) {
        Optional<User> optionalUser = userRepository.findUserByEmail(email);
        return optionalUser.map(domainUserMapper::mapToUserDto)
                .orElseThrow(() -> ResourceNotFoundException.create(User.class, email, "email"));
    }
}
