package com.victor.kochnev.core.facade.feedback;

import com.victor.kochnev.core.dto.domain.entity.FeedbackDto;
import com.victor.kochnev.core.dto.request.CreateOrUpdateFeedbackRequestDto;

public interface FeedbackFacade {
    FeedbackDto createOrUpdate(CreateOrUpdateFeedbackRequestDto requestDto);
}
