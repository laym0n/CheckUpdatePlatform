package com.victor.kochnev.dal.converter;

import com.victor.kochnev.dal.entity.NotificationEntity;
import com.victor.kochnev.domain.entity.Notification;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        uses = {EntityUserMapper.class},
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityNotificationMapper {
    Notification mapToDomain(NotificationEntity notification);

    @Mapping(target = "user", ignore = true)
    NotificationEntity mapToEntity(Notification notification);
}
