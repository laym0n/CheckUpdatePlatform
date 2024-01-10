package com.victor.kochnev.core.converter;

import base.BaseCoreTest;
import com.victor.kochnev.core.dto.UserRegistrationRequestDto;
import com.victor.kochnev.domain.entity.User;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

public class DomainUserMapperTest extends BaseCoreTest {
    @Test
    public void test() {
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
}
