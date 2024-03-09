package com.victor.kochnev.dal.converter;

import com.victor.kochnev.dal.entity.UserEntity;
import com.victor.kochnev.domain.entity.User;
import com.victor.kochnev.domain.enums.UserRole;
import org.mapstruct.*;

import java.util.Arrays;
import java.util.Collection;
import java.util.List;

@Mapper(componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityUserMapper {
    @Mapping(target = "rolesCollection", source = "roles", qualifiedByName = "mapStringToRolesCollection")
    User mapToDomain(UserEntity user);

    @Mapping(target = "roles", source = "rolesCollection", qualifiedByName = "mapRolesCollectionToString")
    UserEntity mapToEntity(User user);

    @Named("mapRolesCollectionToString")
    default String mapRolesCollectionToString(Collection<UserRole> rolesCollection) {
        List<String> stringRolesList = rolesCollection.stream().map(UserRole::name).toList();
        return String.join(",", stringRolesList);
    }

    @Named("mapStringToRolesCollection")
    default Collection<UserRole> mapStringToRolesCollection(String roles) {
        return Arrays.stream(roles.split(","))
                .filter(i -> !i.isBlank())
                .map(UserRole::valueOf)
                .toList();
    }
}
