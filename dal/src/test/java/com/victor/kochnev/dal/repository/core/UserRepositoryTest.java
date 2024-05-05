package com.victor.kochnev.dal.repository.core;

import com.victor.kochnev.core.exception.ResourceNotFoundException;
import com.victor.kochnev.core.repository.UserRepository;
import com.victor.kochnev.dal.BaseBootTest;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.dal.util.TimeUtil;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.entity.builder.UserDomainBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends BaseBootTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    void testCreateUser_WithNullId() {
        //Assign
        User userForCreate = UserDomainBuilder.defaultUser().build();

        //Action
        User savedUser = userRepository.create(userForCreate);

        //Assert
        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getCreateDate());
        assertNotNull(savedUser.getLastChangeDate());
        assertNotNull(savedUser.getVersion());
        assertEquals(UserDomainBuilder.DEFAULT_EMAIL, savedUser.getEmail());
        assertEquals(UserDomainBuilder.DEFAULT_PASSWORD, savedUser.getPassword());

        Optional<UserEntity> optionalUserEntity = userEntityRepository.findById(savedUser.getId());
        assertTrue(optionalUserEntity.isPresent());
    }

    @Test
    void testCreateUser_WithNotNullId() {
        //Assign
        User userForCreate = UserDomainBuilder.persistedDefaultUser().build();

        //Action
        User savedUser = userRepository.create(userForCreate);

        //Assert
        assertNotNull(savedUser.getId());
        assertNotNull(savedUser.getCreateDate());
        assertNotNull(savedUser.getLastChangeDate());
        assertNotNull(savedUser.getVersion());
        assertEquals(UserDomainBuilder.DEFAULT_EMAIL, savedUser.getEmail());
        assertEquals(UserDomainBuilder.DEFAULT_PASSWORD, savedUser.getPassword());

        Optional<UserEntity> optionalUserEntity = userEntityRepository.findById(savedUser.getId());
        assertTrue(optionalUserEntity.isPresent());
    }

    @Test
    void testCreateUser_WithNotNullId_WhenIdAlreadyExists() {
        //Assign
        User userForCreate = UserDomainBuilder.persistedDefaultUser().build();
        UserEntity persistedUser = UserEntityBuilder.persistedDefaultBuilder()
                .id(userForCreate.getId()).build();
        userEntityRepository.save(persistedUser);

        //Action
        assertThrows(Exception.class, () -> userRepository.create(userForCreate));
    }

    @Test
    void testSuccessFindUserByLogin() {
        //Assign
        UserEntity savedUserEntity = userEntityRepository.save(UserEntityBuilder.defaultBuilder().build());

        //Action
        Optional<User> optionalUser = userRepository.findUserByLogin(savedUserEntity.getLogin());

        //Assert
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(savedUserEntity.getId(), user.getId());
        TimeUtil.compareZonedDateTime(savedUserEntity.getCreateDate(), user.getCreateDate());
        TimeUtil.compareZonedDateTime(savedUserEntity.getLastChangeDate(), user.getLastChangeDate());
        assertEquals(savedUserEntity.getVersion(), user.getVersion());
        assertEquals(savedUserEntity.getEmail(), user.getEmail());
        assertEquals(savedUserEntity.getPassword(), user.getPassword());
    }

    @Test
    void testFindUserByLogin_NotExisted_ExpectEmptyOptional() {
        //Action
        Optional<User> optionalUser = userRepository.findUserByLogin("randomLogin");

        //Assert
        assertFalse(optionalUser.isPresent());
    }

    @Test
    void testSuccessFindUserById() {
        //Assign
        UserEntity savedUserEntity = userEntityRepository.save(UserEntityBuilder.defaultBuilder().build());

        //Action
        User user = userRepository.getById(savedUserEntity.getId());

        //Assert
        assertEquals(savedUserEntity.getId(), user.getId());
        TimeUtil.compareZonedDateTime(savedUserEntity.getCreateDate(), user.getCreateDate());
        TimeUtil.compareZonedDateTime(savedUserEntity.getLastChangeDate(), user.getLastChangeDate());
        assertEquals(savedUserEntity.getVersion(), user.getVersion());
        assertEquals(savedUserEntity.getEmail(), user.getEmail());
        assertEquals(savedUserEntity.getPassword(), user.getPassword());
    }

    @Test
    void testFindUserById_NotExisted_ExpectEmptyOptional() {
        //Assign
        UUID userId = UUID.randomUUID();

        //Action
        assertThrows(ResourceNotFoundException.class, () -> userRepository.getById(userId));
    }
}
