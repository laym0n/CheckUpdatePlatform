package com.victor.kochnev.core.service.feedback;

import com.victor.kochnev.core.dto.domain.entity.FeedbackDto;
import com.victor.kochnev.core.dto.request.CreateOrUpdateFeedbackRequestDto;
import com.victor.kochnev.core.dto.request.GetFeedbacksRequestDto;
import com.victor.kochnev.core.dto.response.GetFeedbacksResponseDto;

import java.util.UUID;

public interface FeedbackService {
    FeedbackDto createOrUpdate(UUID userId, CreateOrUpdateFeedbackRequestDto requestDto);

    GetFeedbacksResponseDto getByFilters(GetFeedbacksRequestDto requestDto);
}
