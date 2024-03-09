package com.victor.kochnev.dal.converter;

import com.victor.kochnev.dal.BaseDalTest;
import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.dal.entity.UserEntityBuilder;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.entity.builder.UserDomainBuilder;
import org.junit.jupiter.api.extension.ExtensionContext;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.ArgumentsProvider;
import org.junit.jupiter.params.provider.ArgumentsSource;

import java.util.stream.Stream;

import static org.junit.jupiter.api.Assertions.assertEquals;

class EntityUserMapperTest extends BaseDalTest {
    @ParameterizedTest
    @ArgumentsSource(MapToEntityUserArgumentProvider.class)
    void testMapToEntity(User userForMap, User expectedUser) {
        //Action
        UserEntity mappedUserEntity = entityUserMapper.mapToEntity(userForMap);

        //Assert
        assertEquals(expectedUser.getId(), mappedUserEntity.getId());
        assertEquals(expectedUser.getCreateDate(), mappedUserEntity.getCreateDate());
        assertEquals(expectedUser.getLastChangeDate(), mappedUserEntity.getLastChangeDate());
        assertEquals(expectedUser.getVersion(), mappedUserEntity.getVersion());

        assertEquals(expectedUser.getEmail(), mappedUserEntity.getEmail());
        assertEquals(expectedUser.getPassword(), mappedUserEntity.getPassword());
    }

    @ParameterizedTest
    @ArgumentsSource(MapToDomainUserArgumentProvider.class)
    void testMapToDomain(UserEntity userForMap, UserEntity expectedUser) {
        //Action
        User mappedUser = entityUserMapper.mapToDomain(userForMap);

        //Assert
        assertEquals(expectedUser.getId(), mappedUser.getId());
        assertEquals(expectedUser.getCreateDate(), mappedUser.getCreateDate());
        assertEquals(expectedUser.getLastChangeDate(), mappedUser.getLastChangeDate());
        assertEquals(expectedUser.getVersion(), mappedUser.getVersion());

        assertEquals(expectedUser.getEmail(), mappedUser.getEmail());
        assertEquals(expectedUser.getPassword(), mappedUser.getPassword());
    }

    static class MapToEntityUserArgumentProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            User notPersistedUser = UserDomainBuilder.defaultUser().build();
            User persistedUser = UserDomainBuilder.persistedDefaultUser().build();
            return Stream.of(
                    Arguments.of(notPersistedUser, notPersistedUser.toBuilder().build()),
                    Arguments.of(persistedUser, persistedUser.toBuilder().build())
            );
        }
    }

    static class MapToDomainUserArgumentProvider implements ArgumentsProvider {

        @Override
        public Stream<? extends Arguments> provideArguments(ExtensionContext extensionContext) throws Exception {
            UserEntity notPersistedUser = UserEntityBuilder.defaultBuilder().build();
            UserEntity persistedUser = UserEntityBuilder.persistedDefaultBuilder().build();
            UserEntity persistedUserWithoutRoles = UserEntityBuilder.persistedDefaultBuilder().roles("").build();
            return Stream.of(
                    Arguments.of(notPersistedUser, notPersistedUser.toBuilder().build()),
                    Arguments.of(persistedUser, persistedUser.toBuilder().build()),
                    Arguments.of(persistedUserWithoutRoles, persistedUserWithoutRoles.toBuilder().build())
            );
        }
    }
}
