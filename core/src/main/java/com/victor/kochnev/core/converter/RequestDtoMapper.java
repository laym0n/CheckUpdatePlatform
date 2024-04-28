package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.dal.GetPluginUsagesDalRequestDto;
import com.victor.kochnev.core.dto.dal.GetPluginsDalRequestDto;
import com.victor.kochnev.core.dto.dal.GetTasksDalRequestDto;
import com.victor.kochnev.core.dto.dal.GetWebResourceObservingDalRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginUsagesRequestDto;
import com.victor.kochnev.core.dto.request.GetPluginsRequestDto;
import com.victor.kochnev.core.dto.request.GetTasksRequestDto;
import com.victor.kochnev.core.dto.request.GetWebResourceObservingsRequestDto;
import com.victor.kochnev.core.security.entity.UserSecurity;
import com.victor.kochnev.core.security.service.user.SecurityUserService;
import com.victor.kochnev.domain.enums.UserRole;
import org.mapstruct.*;
import org.springframework.beans.factory.annotation.Autowired;

import java.util.Collections;
import java.util.List;
import java.util.UUID;

@Mapper(injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public abstract class RequestDtoMapper {
    @Autowired
    private SecurityUserService securityUserService;

    @Mapping(target = "filters.pluginUsageUserId", ignore = true)
    @Mapping(target = "filters.statuses", ignore = true)
    @Mapping(target = "filters.ownerIds", ignore = true)
    public abstract GetPluginsDalRequestDto mapToDal(GetPluginsRequestDto request);

    @Mapping(target = "filters.userIds", ignore = true)
    public abstract GetWebResourceObservingDalRequestDto mapToDal(GetWebResourceObservingsRequestDto request);

    @Mapping(target = "filters.userIds", ignore = true)
    public abstract GetPluginUsagesDalRequestDto mapToDal(GetPluginUsagesRequestDto requestDto);

    @Mapping(target = "filters.ownerIds", expression = "java(mapOwnerIds())")
    public abstract GetTasksDalRequestDto mapToDal(GetTasksRequestDto requestDto);

    protected List<UUID> mapOwnerIds() {
        UserSecurity curUser = securityUserService.getCurrentUser();
        var curUserRoles = curUser.getRoles();
        boolean currentUserIsEmployee = curUserRoles.stream().anyMatch(role -> role == UserRole.EMPLOYEE || role == UserRole.ADMIN);
        return currentUserIsEmployee ? Collections.emptyList() : List.of(curUser.getId());
    }
}
