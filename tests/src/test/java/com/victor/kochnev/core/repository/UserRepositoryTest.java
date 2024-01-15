package com.victor.kochnev.core.repository;

import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.entity.builder.UserEntityBuilder;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.entity.builder.UserDomainBuilder;
import com.victor.kochnev.tests.base.BaseIntegrationTest;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Optional;

import static com.victor.kochnev.tests.util.TimeUtil.compareZonedDateTime;
import static org.junit.jupiter.api.Assertions.*;

class UserRepositoryTest extends BaseIntegrationTest {
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
        UserEntity persistedUser = UserEntityBuilder.persistedDefaultEntityUser()
                .id(userForCreate.getId()).build();
        userEntityRepository.save(persistedUser);

        //Action
        assertThrows(Exception.class, () -> userRepository.create(userForCreate));
    }

    @Test
    void testFindUserByEmail_Existed() {
        //Assign
        UserEntity savedUserEntity = userEntityRepository.save(UserEntityBuilder.defaultEntityUser().build());

        //Action
        Optional<User> optionalUser = userRepository.findUserByEmail(savedUserEntity.getEmail());

        //Assert
        assertTrue(optionalUser.isPresent());
        User user = optionalUser.get();
        assertEquals(savedUserEntity.getId(), user.getId());
        compareZonedDateTime(savedUserEntity.getCreateDate(), user.getCreateDate());
        compareZonedDateTime(savedUserEntity.getLastChangeDate(), user.getLastChangeDate());
        assertEquals(savedUserEntity.getVersion(), user.getVersion());
        assertEquals(savedUserEntity.getEmail(), user.getEmail());
        assertEquals(savedUserEntity.getPassword(), user.getPassword());
    }

    @Test
    void testFindUserByEmail_NotExisted() {
        //Action
        Optional<User> optionalUser = userRepository.findUserByEmail("victor_k02@mail.ru");

        //Assert
        assertFalse(optionalUser.isPresent());
    }
}
