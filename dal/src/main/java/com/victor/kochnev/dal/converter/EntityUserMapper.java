package com.victor.kochnev.dal.converter;

import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.domain.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface EntityUserMapper {
    User mapToDomain(UserEntity user);

    UserEntity mapToEntity(User user);
}
