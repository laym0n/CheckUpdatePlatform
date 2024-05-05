package com.victor.kochnev.core.converter;

import com.victor.kochnev.core.dto.dal.GetFeedbacksDalResponseDto;
import com.victor.kochnev.core.dto.domain.entity.FeedbackDto;
import com.victor.kochnev.core.dto.request.CreateOrUpdateFeedbackRequestDto;
import com.victor.kochnev.core.dto.response.GetFeedbacksResponseDto;
import com.victor.kochnev.domain.entity.Feedback;
import org.mapstruct.*;

@Mapper(uses = {DomainPluginMapper.class, DomainUserMapper.class},
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface DomainFeedbackMapper {
    FeedbackDto mapToDto(Feedback feedback);

    @BlankEntityMapping
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "plugin", ignore = true)
    void update(@MappingTarget Feedback feedback, CreateOrUpdateFeedbackRequestDto requestDto);

    @BlankEntityMapping
    @Mapping(target = "user", ignore = true)
    @Mapping(target = "plugin", ignore = true)
    Feedback mapToDomain(CreateOrUpdateFeedbackRequestDto requestDto);

    GetFeedbacksResponseDto mapToDto(GetFeedbacksDalResponseDto dalResponseDto);
}
