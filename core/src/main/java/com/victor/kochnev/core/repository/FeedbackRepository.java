package com.victor.kochnev.core.repository;

import com.victor.kochnev.core.dto.dal.GetFeedbacksDalRequestDto;
import com.victor.kochnev.core.dto.dal.GetFeedbacksDalResponseDto;
import com.victor.kochnev.domain.entity.Feedback;

import java.util.Optional;
import java.util.UUID;

public interface FeedbackRepository {

    Feedback create(Feedback feedback);

    Feedback update(Feedback feedback);

    Optional<Feedback> findByUserIdAndPluginId(UUID userId, UUID pluginId);

    GetFeedbacksDalResponseDto getByFilters(GetFeedbacksDalRequestDto requestDto);
}
