package com.victor.kochnev.core.repository;

import com.victor.kochnev.base.BaseIntegrationTest;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.entity.builder.UserEntityBuilder;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.entity.builder.UserDomainBuilder;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;

public class UserRepositoryTest extends BaseIntegrationTest {
    @Autowired
    private UserRepository userRepository;

    @Test
    public void testCreateUser_WithNullId() {
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
    public void testCreateUser_WithNotNullId() {
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
    public void testCreateUser_WithNotNullId_WhenIdAlreadyExists() {
        //Assign
        User userForCreate = UserDomainBuilder.persistedDefaultUser().build();
        UserEntity persistedUser = UserEntityBuilder.persistedDefaultEntityUser()
                .id(userForCreate.getId()).build();
        userEntityRepository.save(persistedUser);

        //Action
        assertThrows(Exception.class, () -> userRepository.create(userForCreate));
    }
}
