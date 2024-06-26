package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.BaseCoreUnitTest;
import com.victor.kochnev.core.dto.domain.entity.UserDto;
import com.victor.kochnev.core.dto.request.UserRegistrationRequestDto;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.entity.builder.UserDomainBuilder;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.ValueSource;

import static org.junit.jupiter.api.Assertions.*;

class DomainUserMapperUnitTest extends BaseCoreUnitTest {

    @Test
    void testMapToEntity() {
        //Assign
        String email = "victor_k02@mail.ru";
        String password = "password";
        UserRegistrationRequestDto request = UserRegistrationRequestDto.builder()
                .email(email)
                .password(password)
                .build();

        //Action
        User mappedUser = domainUserMapper.mapToEntity(request);

        //Assert
        assertNull(mappedUser.getId());
        assertNull(mappedUser.getCreateDate());
        assertNull(mappedUser.getLastChangeDate());
        assertNull(mappedUser.getVersion());

        assertEquals(email, mappedUser.getEmail());
        assertEquals(password, mappedUser.getPassword());
        assertTrue(mappedUser.isEnabled());
    }

    @ParameterizedTest
    @ValueSource(booleans = {true, false})
    void testMapToUserDto(boolean isEnabled) {
        //Assign
        User user = UserDomainBuilder.persistedDefaultUser().enabled(isEnabled).build();

        //Action
        UserDto mappedUserDomainDto = domainUserMapper.mapToUserDto(user);

        //Assert
        assertEquals(user.getId(), mappedUserDomainDto.getId());
        assertEquals(user.getEmail(), mappedUserDomainDto.getEmail());
        assertEquals(user.getPassword(), mappedUserDomainDto.getPassword());
        assertEquals(isEnabled, mappedUserDomainDto.getEnabled());
    }
}
