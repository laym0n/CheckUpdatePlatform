package com.victor.kochnev.dal.converter;

import com.victor.kochnev.dal.entity.FeedbackEntity;
import com.victor.kochnev.domain.entity.Feedback;
import org.mapstruct.*;

@Mapper(uses = {EntityUserMapper.class, EntityPluginMapper.class},
        componentModel = "spring",
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface EntityFeedbackMapper {

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "plugin", ignore = true)
    FeedbackEntity mapToDal(Feedback feedback);

    Feedback mapToDomain(FeedbackEntity feedbackEntity);

    @Mapping(target = "user", ignore = true)
    @Mapping(target = "plugin", ignore = true)
    void update(@MappingTarget FeedbackEntity feedbackEntity, Feedback feedback);
}
