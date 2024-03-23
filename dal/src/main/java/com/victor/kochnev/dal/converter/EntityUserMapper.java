package com.victor.kochnev.dal.converter;

import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.domain.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityUserMapper {
    User mapToDomain(UserEntity user);

    UserEntity mapToEntity(User user);
}
