package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.dal.converter.EntityUserMapper;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.repository.jpa.UserEntityRepository;
import com.victor.kochnev.domain.entity.User;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

@Repository
@RequiredArgsConstructor
public class UserRepositoryImpl implements UserRepository {
    private final UserEntityRepository userEntityRepository;
    private final EntityUserMapper entityUserMapper;

    @Override
    public Optional<User> findUserByLogin(String login) {
        return userEntityRepository.findByLogin(login).map(entityUserMapper::mapToDomain);
    }

    @Override
    public User create(User user) {
        if (Objects.nonNull(user.getId()) && userEntityRepository.existsById(user.getId())) {
            throw new RuntimeException("User with id " + user.getId() + " already exists");
        }
        UserEntity savedUser = userEntityRepository.save(entityUserMapper.mapToEntity(user));
        return entityUserMapper.mapToDomain(savedUser);
    }

    @Override
    public User getById(UUID id) {
        UserEntity userEntity = userEntityRepository.findById(id)
                .orElseThrow(() -> ResourceNotFoundException.create(User.class, id.toString(), "id"));
        return entityUserMapper.mapToDomain(userEntity);
    }
}
