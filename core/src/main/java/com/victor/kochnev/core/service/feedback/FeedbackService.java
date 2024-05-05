package com.victor.kochnev.core.service.feedback;

import com.victor.kochnev.core.dto.domain.entity.FeedbackDto;
import com.victor.kochnev.core.dto.request.CreateOrUpdateFeedbackRequestDto;

import java.util.UUID;

public interface FeedbackService {
    FeedbackDto createOrUpdate(UUID userId, CreateOrUpdateFeedbackRequestDto requestDto);
}
