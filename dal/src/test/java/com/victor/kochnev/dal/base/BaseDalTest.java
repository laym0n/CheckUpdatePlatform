package com.victor.kochnev.dal.base;

import com.victor.kochnev.dal.converter.EntityUserMapper;
import com.victor.kochnev.dal.converter.EntityUserMapperImpl;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Spy;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
public abstract class BaseDalTest {
    @Spy
    protected EntityUserMapper entityUserMapper = new EntityUserMapperImpl();
}
