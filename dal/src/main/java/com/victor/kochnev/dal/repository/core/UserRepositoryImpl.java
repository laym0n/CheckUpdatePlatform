package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.dal.converter.EntityUserMapper;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.repository.jpa.UserEntityRepository;
import com.victor.kochnev.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserEntityRepository userEntityRepository;
    private final EntityUserMapper entityUserMapper;

    @Override
    public Optional<User> findUserByEmail(String email) {
        return userEntityRepository.findByEmail(email).map(entityUserMapper::mapToDomain);
    }

    @Override
    public User create(User user) {
        if (Objects.nonNull(user.getId()) && userEntityRepository.existsById(user.getId())) {
            throw new RuntimeException("User with id " + user.getId() + " already exists");
        }
        UserEntity savedUser = userEntityRepository.save(entityUserMapper.mapToEntity(user));
        return entityUserMapper.mapToDomain(savedUser);
    }
}
